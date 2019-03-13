package release_manager.model

import tiny.*
import release_manager.dao.*
import release_manager.domain.*

private val sitePhrase = TinyApp.getConfig()["site.phrase"]
private val userDao = UserDao()
private val userModel = UserModel()

class AdminModel {

	fun newUser(username: String, password: String, role: String = "user"): TinyResult<Long> {
		val passwordMd5 = userModel.getPasswordMd5(password)
		return userDao.new(username, passwordMd5, role)
	}

	fun updateUser(id: Long, password: String, role: String = "user"): TinyResult<Int> {
		var passwordMd5 = ""
		if(password != "") {
			passwordMd5 = userModel.getPasswordMd5(password)
		}
		return userDao.update(id, passwordMd5, role)
	}

	fun listUser(offset: Long = 0L, pageSize: Long = 10L): TinyResult<List<User>> {
		return userDao.list(offset, pageSize)
	}

	fun isUserNameFree(username: String): TinyResult<Boolean> {
		val trUser = userDao.getUserByName(username)
		if(trUser.error()){
			return TinyResult<Boolean>("get user error:" + trUser.cause(), false)
		}
		val user = trUser.data()
		if(user == null){
			return TinyResult<Boolean>(null, true)
		}
		return TinyResult<Boolean>(null, false)
	}

	fun deleteUser(id: Long): TinyResult<Int>{
		return userDao.delete(id)
	}
}