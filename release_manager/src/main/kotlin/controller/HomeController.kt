package release_manager.controller

import tiny.annotation.Controller
import release_manager.model.*
import release_manager.domain.*
import tiny.*

private val userModel = UserModel()
private val siteConfigModel = SiteConfigModel()

@Controller
class HomeController : BaseController() {

	private var _siteId = 0L
	private var _siteInfo: SiteConfig? = null

	override fun before() {
		super.before()
		userModel.hasLoggedin(true)

		_siteId = ctx.params.getLong("siteId")
		if(_siteId > 0L) {
			_siteInfo = siteConfigModel.getById(_siteId).data()
		}
	}

	fun indexAction(): Any {
		val task = ctx.params["task"]
		
		return "this is the home/index action"
	}
}