package release_manager.controller

import tiny.annotation.Controller
import release_manager.model.*
import release_manager.service.*
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
		view["siteInfo"] = null
		view["siteId"] = _siteId
		if(_siteId > 0L) {
			_siteInfo = siteConfigModel.getById(_siteId).data()
			view["siteInfo"] = _siteInfo
		}
	}

	fun indexAction(): Any {
		val task = ctx.params["task"]
		val releaseType = ctx.params["releaseType"]
		val filterKeywords = ctx.params["keyWords"]

		val frameLink = """src="/home/runCommand?siteId=${_siteId}&task=${task}&releaseType=${releaseType}" """
		view["frameLink"] = frameLink
		view["releaseType"] = releaseType
		view["keyWords"] = filterKeywords

		val sites = siteConfigModel.list(offset = 0, pageSize = 1000).data()
		view["sites"] = sites

		return render("home/index")
	}

	fun runCommandAction(): Unit {
		val command = ctx.params["task"]
		val siteId = ctx.params.getLong("siteId")

		val releasService = ReleaseService()
		val writer = ctx.response.getWriter()
		releasService.runCommand(writer, siteId, command)
		return
	}
}