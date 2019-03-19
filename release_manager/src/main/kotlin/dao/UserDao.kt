package release_manager.dao

import tiny.*
import tiny.lib.*
import release_manager.domain.*

private val db: TinyJdbc = TinyRegistry["db.release_manager"] 

class UserDao {

	fun new(username: String, password: String, role: String = "user"): TinyResult<Long> {
		if(username == "" || password == "") {
			return TinyResult("bad param", null as Long?)
		}
		val p = mapOf(
				"username" to username,
				"password" to password,
				"role" to role
			)
		val result = db.insert("""insert into users 
			(`username`,`password`,`role`) values (:username, :password, :role)""", p)

		return TinyResult<Long>(result)
	}

	fun update(id: Long, password: String, role: String = "user"): TinyResult<Int> {
		if(id < 1) {
			return TinyResult("invalid id", null as Int?)
		}
		var passwordSql = ""
		if(password != "") {
			passwordSql = "`password` = :password,"
		}
		val p = mapOf(
				"id" to id,
				"password" to password,
				"role" to role
			)
		val result = db.update("update users set ${passwordSql} `role` = :role where id = :id", p)

		return TinyResult<Int>(result)
	}

	fun delete(id: Long): TinyResult<Int>{
		if(id < 1) {
			return TinyResult("invalid id", null as Int?)
		}
		val p = mapOf(
				"id" to id
			)
		val result = db.update("delete from users where id = :id", p)
		return TinyResult<Int>(result)
	}

	fun list(offset: Long = 0L, pageSize: Long = 10L): TinyResult<List<User>> {
		val p = mapOf(
				"offset" to offset,
				"pageSize" to pageSize
			)
		val result = db.queryForList("select * from users limit :offset, :pageSize", p)
		return TinyResult.fromList(result, User::class)
	}

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