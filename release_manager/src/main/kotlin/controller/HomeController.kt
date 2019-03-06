package release_manager.controller

import tiny.annotation.Controller

@Controller
class HomeController : BaseController() {

	fun indexAction(): Any {
		return "this is the home/index action"
	}
}