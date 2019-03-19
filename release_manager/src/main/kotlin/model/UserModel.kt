package release_manager.model

import tiny.*
import release_manager.dao.*
import release_manager.domain.*
import release_manager.library.Utils

private val sitePhrase = TinyApp.getConfig()["site.phrase"]
private val userDao = UserDao()

class UserModel {

	fun currentUserId(): Long {
		val userid: Long = TinyRouter.ctx().session["uid"] ?: 0L
		return userid
	}

	fun currentRole(): String {
		val role: String = TinyRouter.ctx().session["role"] ?: ""
		return role
	}

	fun currentUserInfo(): TinyResult<User?> {
		val userid = currentUserId()
		return getUserById(userid)
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

	fun getUserById(userid: Long): TinyResult<User?> {
		return userDao.getUserById(userid)
	}

	/*user login*/
	fun login(username: String, password: String): TinyResult<Boolean> {
		if(username == "" || password == "") {
			return TinyResult(null, false)
		}

		val result = userDao.getUserByName(username)
		if(result.error()) {
			TinyLog.log("login:getUserByName error: ${result.cause}", this::class.simpleName!!)
			return TinyResult("internal error", result.cause())
		}
		val user = result.data()
		if(user == null) {
			return TinyResult(null, false)
		}
		val passwordMd5 = getPasswordMd5(password)
		if(user.password == passwordMd5) { //login success
			val session = TinyRouter.ctx().session
			session["uid"] = user.id
			session["username"] = user.username
			session["role"] = user.role

			LogModel.add(user.id, user.username, "login", "Success")

			return TinyResult(null, true)
		}

		LogModel.add(-1, user.username, "login", "Failed")

		return TinyResult(null, false)
	}

	/*only for demo purpose, should be verifyPassword(userid: Long, password: String)*/
	fun verifyPassword(password: String): TinyResult<Boolean> {
		val session = TinyRouter.ctx().session
		val userid: Long = session["uid"] ?: 0 
		val username: String = session["username"] ?: "" 
		if(userid < 1 || username == "" || password == "") {
			return TinyResult(null, false)
		}

		val result = userDao.getUserByName(username)
		if(result.error()) {
			return TinyResult("getUserByName error", false)
		}
		val user = result.data()
		if(user == null) {
			return TinyResult(null, false)
		}
		val passwordMd5 = getPasswordMd5(password)
		if(user.password == passwordMd5) {
			return TinyResult(null, true)
		}
		return TinyResult(null, false)
	}

	/*only for demo purpose, should be changePassword(userid: Long, password: String)*/
	fun changePassword(password: String): TinyResult<Boolean> {
		val session = TinyRouter.ctx().session
		val userid: Long = session["uid"] ?: 0 
		
		if(userid < 1 || password == "") {
			return TinyResult("bad param", false)
		}
		val passwordMd5 = getPasswordMd5(password)
		val result = userDao.updatePassword(userid, passwordMd5)
		if(result.error()) {
			return TinyResult("updatePassword failed", false)
		}
		return TinyResult(null, true)
	}

	public fun getPasswordMd5(password: String): String{
		return Utils.md5(password + sitePhrase)
	}
}