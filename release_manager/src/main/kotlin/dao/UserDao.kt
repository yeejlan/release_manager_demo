package release_manager.dao

import tiny.*
import tiny.lib.*
import release_manager.domain.*

private val db = TinyRegistry["db.release_manager"] as TinyJdbc

class UserDao {

	fun getUserByName(username: String): TinyResult<User?> {
		if(username == "") {
			return TinyResult("username is empty", null as User?)
		}

		val p = mapOf(
				"username" to username
			)

		val result = db.queryForMap("select * from users where username = :username", p)
		return TinyResult.fromMap(result, User::class)
	}

	fun getUserById(userid: Long): TinyResult<User?> {
		if(userid < 1) {
			return TinyResult("userid is invalid", null as User?)
		}

		val p = mapOf(
				"id" to userid
			)

		val result = db.queryForMap("select * from users where id = :id", p)
		return TinyResult.fromMap(result, User::class)
	}

	fun updatePassword(userid: Long, password: String): TinyResult<Int> {
		if(userid < 1) {
			return TinyResult("userid is invalid", null as Int?)
		}else if(password == "") {
			return TinyResult("password is empty", null as Int?)
		}

		val p = mapOf(
				"password" to password,
				"id" to userid
			)

		val result = db.update("update users set password = :password where id = :id", p)
		return TinyResult(result)
	}

}