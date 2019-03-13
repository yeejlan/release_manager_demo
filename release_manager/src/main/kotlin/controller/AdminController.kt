package release_manager.controller

import tiny.annotation.Controller
import release_manager.model.*
import release_manager.domain.*
import tiny.*

private val userModel = UserModel()
private val adminModel = AdminModel()

@Controller
class AdminController : BaseController() {

	override fun before() {
		super.before()
		userModel.hasLoggedin(true)
		userModel.isAdmin(true)
	}

	fun indexAction(): Any {
		//user list in single page
		val page = 1L
		val numPerPage= 1000L

		val offset = (page - 1) * numPerPage
		val userList = adminModel.listUser(offset, numPerPage).data()

		view["userlist"] = userList

		return render("admin/list")
	}

	fun addAction(): Any {
		view["post"] = mapOf(
				"username" to "",
				"password" to "",
				"confirmpassword" to "",
				"role" to ""
			)
		view["errStr"] = ""		
		return render("admin/add")
	}

	fun doaddAction(): Any{
		
		val username = ctx.params["username"]
		val password = ctx.params["password"]
		val confirmpassword = ctx.params["confirmpassword"]
		val role = ctx.params["role"]

		var err = ""

		if(username == "" || password == "" || confirmpassword == ""){
			err = "Please fill out Username/Password/Confirm Password!";
		}
		if(password != confirmpassword){
			err = "Confirm Password does NOT match Password!";
		}
				
		if(err == ""){
			val result = adminModel.newUser(username, password, role)
			if(result.error()){
				err = "new user failed"
			}else{
				ctx.response.sendRedirect("/admin")
				TinyRouter.exit()
			}
		}

		view["post"] = ctx.params.getMap()
		view["errStr"] = err
		return render("admin/add")
	}

	fun editAction(): Any {
		
		val id = ctx.params.getLong("id")

		val user = userModel.getUserById(id).data()

		view["user"] = user
		view["errStr"] = ""
		
		return render("admin/edit")
	}

	fun doeditAction(): Any {

		val id = ctx.params.getLong("id")
		val username = ctx.params["username"]
		val password = ctx.params["password"]
		val confirmpassword = ctx.params["confirmpassword"]
		val role = ctx.params["role"]

		var err = ""

		if(password == "" || confirmpassword == ""){
			err = "Please fill out Password/Confirm Password!";
		}
		if(password != confirmpassword){
			err = "Confirm Password does NOT match Password!";
		}
		if(err == ""){
			val result = adminModel.updateUser(id, password, role)
			if(result.error()) {
				err = "update user failed"
			}else{
				ctx.response.sendRedirect("/admin")
				TinyRouter.exit()
			}
		}

		view["user"] = User(hashMapOf(
				"id" to id,
				"password" to "",
				"username" to username,
				"role" to role
			))
		view["errStr"] = err
		return render("admin/edit")
	}

	fun deleteAction(){

		val id = ctx.params.getLong("id")

		val result = adminModel.deleteUser(id)
		result.mayThrow("delete user failed")
		ctx.response.sendRedirect("/admin")
	}

}