package release_manager.controller

import tiny.annotation.Controller
import release_manager.model.*
import tiny.*

private val userModel = UserModel()

@Controller
class LoginController : BaseController() {

	override fun before() {
		super.before()
		view["errStr"] = ""
	}

	fun indexAction(): Any {
		val err = ctx.params.getInt("err")

		if(err == 1) {
			view["errStr"] = "Invalid username or password, Please try again!"
		}
		if(err == 2) {
			view["errStr"] = "Internal error, Please try again later!"
		}

		return render("login/index")
	}

	fun postAction(): Unit {
		val username = ctx.params["username"]
		val password = ctx.params["password"]
		val loginResult = userModel.login(username, password)
		if(loginResult.error()){
			TinyLog.log("login error: " + loginResult.cause(), this::class.simpleName!!)
			ctx.response.sendRedirect("/login?err=2")
			return
		}
		if(loginResult.data() == true) {
			ctx.response.sendRedirect("/")
			return
		}
		ctx.response.sendRedirect("/login?err=1")
		return
	}

	fun exitAction(): Unit {
		ctx.session.destroy()
		ctx.response.sendRedirect("/login")
		return
	}

	fun changePasswordAction(): Any {
		val oldPassword = ctx.params["oldpassword"]
		val newPassword  = ctx.params["newpassword"]
		val confirmPassword = ctx.params["confirmpassword"]

		view["msg"] = ""
		view["oldPassword"] = oldPassword
		view["newPassword"] = newPassword
		view["confirmPassword"] = confirmPassword
		if(ctx.params.getMap().size > 0) {
			var msg = ""
			val result = userModel.verifyPassword(oldPassword)
			if(result.error()) {
				msg = "Internal error on verifyPassword"
			}
			if(msg == "" && oldPassword != "" && result.data() == false){
				msg = "Old Password is wrong!"
			}
			if(msg == "" && (newPassword == "" || confirmPassword == "")){
				msg = "New Password or Comfirm New Passsword cannot be empty!"
			}
			if(msg == "" && (newPassword != confirmPassword)){
				msg = "Confirm New Password do NOT match New Password!"
			}
			if(msg == "") {
				val updateResult = userModel.changePassword(newPassword)
				if(updateResult.error()) {
					msg = "Internal error on changePassword"
				}else {
					msg = "Your password updated successfully!"
				}
			}
			view["msg"] = msg
		}
		return render("login/changepassword")
	}
}

private class LoginControllerException(message: String?) : Throwable(message)