package release_manager.controller

import tiny.annotation.Controller

@Controller
class LoginController : BaseController() {

	fun indexAction(): Any {
		view["errStr"] = ""
		return render("login/index")
	}
}