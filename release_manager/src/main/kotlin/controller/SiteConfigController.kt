package release_manager.controller

import tiny.annotation.Controller
import release_manager.model.*
import release_manager.domain.*
import tiny.*

private val userModel = UserModel()
private val siteConfigModel = SiteConfigModel()

@Controller
class SiteConfigController : BaseController() {

	override fun before() {
		super.before()
		userModel.hasLoggedin(true)
		userModel.isAdmin(true)
	}

	fun indexAction(): Any {
		//list in single page
		val page = 1L
		val numPerPage= 1000L

		val offset = (page - 1) * numPerPage
		val sites = siteConfigModel.list(offset, numPerPage).data()

		view["sites"] = sites

		return render("siteconfig/list")
	}

	fun addAction(): Any {
		view["siteconfig"] = SiteConfig(hashMapOf(
				"id" to 0,
				"sitename" to "",
				"base_dir" to "",
				"get_current_branch_command" to "",
				"update_command" to "",
				"generate_command" to "",
				"test_release_command" to "",
				"release_command" to "",
				"cache_dir" to "",
				"cache_exclude_dir" to "",
				"cache_urls" to ""
			))
		view["errStr"] = ""
		return render("siteconfig/add")
	}

	fun doaddAction(): Any{
		
		val siteconfig = SiteConfig(hashMapOf(
				"id" to 0,
				"sitename" to ctx.params["sitename"],
				"base_dir" to ctx.params["base_dir"],
				"get_current_branch_command" to ctx.params["get_current_branch_command"],
				"update_command" to ctx.params["update_command"],
				"generate_command" to ctx.params["generate_command"],
				"test_release_command" to ctx.params["test_release_command"],
				"release_command" to ctx.params["release_command"],
				"cache_dir" to ctx.params["cache_dir"],
				"cache_exclude_dir" to ctx.params["cache_exclude_dir"],
				"cache_urls" to ctx.params["cache_urls"]
			))

		var err = ""
		if(siteconfig.sitename == ""){
			err = "Invalid sitename";
		}
				
		if(err == ""){
			val result = siteConfigModel.new(siteconfig)
			if(result.error()){
				err = "new siteconfig failed"
			}else{
				ctx.response.sendRedirect("/siteconfig")
				TinyRouter.exit()
			}
		}

		view["siteconfig"] = siteconfig
		view["errStr"] = err
		return render("siteconfig/add")
	}

	fun editAction(): Any {
		
		val id = ctx.params.getLong("id")

		val siteconfig = siteConfigModel.getById(id).data()

		view["siteconfig"] = siteconfig
		view["errStr"] = ""
		
		return render("siteconfig/edit")
	}

	fun doeditAction(): Any {

		val siteconfig = SiteConfig(hashMapOf(
				"id" to ctx.params.getLong("id"),
				"sitename" to ctx.params["sitename"],
				"base_dir" to ctx.params["base_dir"],
				"get_current_branch_command" to ctx.params["get_current_branch_command"],
				"update_command" to ctx.params["update_command"],
				"generate_command" to ctx.params["generate_command"],
				"test_release_command" to ctx.params["test_release_command"],
				"release_command" to ctx.params["release_command"],
				"cache_dir" to ctx.params["cache_dir"],
				"cache_exclude_dir" to ctx.params["cache_exclude_dir"],
				"cache_urls" to ctx.params["cache_urls"]
			))

		var err = ""

		if(siteconfig.id < 1){
			err = "Invalid id";
		}
		if(siteconfig.sitename == ""){
			err = "Please fill out the Site Name!";
		}
		if(err == ""){
			val result = siteConfigModel.update(siteconfig)
			if(result.error()) {
				err = "update siteconfig failed"
			}else{
				ctx.response.sendRedirect("/siteconfig")
				TinyRouter.exit()
			}
		}

		view["siteconfig"] = siteconfig
		view["errStr"] = err
		return render("siteconfig/edit")
	}

	fun deleteAction(){

		val id = ctx.params.getLong("id")

		val result = siteConfigModel.delete(id)
		result.mayThrow("delete siteconfig failed")
		ctx.response.sendRedirect("/siteconfig")
	}
	
}