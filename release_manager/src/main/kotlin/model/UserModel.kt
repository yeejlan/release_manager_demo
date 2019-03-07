package release_manager.model

import tiny.*
import release_manager.dao.*
import release_manager.domain.*
import release_manager.library.Utils

private val sitePhrase = TinyApp.getConfig()["site.phrase"]
private val userDao = UserDao()
private val logModel = LogModel()

class UserModel {

	fun currentUserId(): Long {
		val userid = TinyRouter.ctx().session["uid"] ?: 0L
		return userid as Long
	}

	fun currentRole(): String {
		val role = TinyRouter.ctx().session["role"] ?: ""
		return role as String
	}

	fun currentUserInfo(): User {
		return User(hashMapOf())
	}

	/*call this function when a page need user auth*/
	fun hasLoggedin(loginPageRedirect: Boolean = false): Boolean {
		if(currentUserId() > 0) {
			return true
		}
		if(loginPageRedirect) {
			TinyRouter.ctx().response.sendRedirect("/login")
			TinyRouter.exit()
		}
		return false
	}

	/*call this function when a page need admin privilege*/
	fun isAdmin(pageRedirect: Boolean = true): Boolean {
		if(currentRole() == "admin") {
			return true
		}
		if(pageRedirect) {
			TinyRouter.ctx().response.sendRedirect("/")
			TinyRouter.exit()
		}
		return false
	}

	/*user login*/
	fun login(username: String, password: String): TinyResult<Boolean> {
		if(username == "" || password == "") {
			return TinyResult("bad param", false)
		}

		val result = userDao.getUserByName(username)
		if(result.error()) {
			TinyLog.log("login:getUserByName error: ${result.cause}", this::class.simpleName!!)
			return TinyResult("internal error", result.cause())
		}
		val user = result.data()
		val passwordMd5 = _getPasswordMd5(username, password)
		if(user.password == passwordMd5) { //login success
			val session = TinyRouter.ctx().session
			session["uid"] = user.id
			session["username"] = user.username
			session["role"] = user.role

			logModel.add(user.id, user.username, "login", "Success")

			return TinyResult(null, true)
		}

		logModel.add(-1, user.username, "login", "Failed")

		return TinyResult("login failed", false)
	}

	/*only for demo purpose, should be verifyPassword(userid: Long, password: String)*/
	fun verifyPassword(password: String): TinyResult<Boolean> {
		val session = TinyRouter.ctx().session
		val userid = session["uid"] as Long
		val username = session["username"] as String
		if(userid < 1 || username == "" || password == "") {
			return TinyResult(null, false)
		}

		val result = userDao.getUserByName(username)
		if(result.error()) {
			return TinyResult("getUserByName error", false)
		}
		val user = result.data()
		val passwordMd5 = _getPasswordMd5(username, password)
		if(user.password == passwordMd5) {
			return TinyResult(null, true)
		}
		return TinyResult(null, false)
	}

	/*only for demo purpose, should be changePassword(userid: Long, password: String)*/
	fun changePassword(password: String): TinyResult<Boolean> {
		val session = TinyRouter.ctx().session
		val userid = session["uid"] as Long
		
		if(userid < 1 || password == "") {
			return TinyResult("bad param", false)
		}

		val result = userDao.updatePassword(userid, password)
		if(result.error()) {
			return TinyResult("updatePassword failed", false)
		}
		return TinyResult(null, true)
	}

	private fun _getPasswordMd5(username: String, password: String): String{
		return Utils.md5(username + password + sitePhrase)
	}
}