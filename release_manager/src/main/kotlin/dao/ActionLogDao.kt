package release_manager.dao

import tiny.*
import tiny.lib.*
import release_manager.domain.*
import java.util.Date
import release_manager.library.Utils

private val db = TinyRegistry["db.release_manager"] as TinyJdbc

class ActionLogDao {

	fun add(userid: Long, username: String, action_name: String, return_message: String): TinyResult<Long> {

		val p = mapOf(
				"userid" to userid,
				"username" to username,
				"action_name" to action_name,
				"return_message" to return_message,
				"log_date" to Date(),
				"log_ip" to Utils.getClientIp()
			)

		val result = db.insert("""insert into action_log 
		(`userid`,`username`,`action_name`,`return_message`,`log_date`,`log_ip`) 
		values (:userid, :username, :action_name, :return_message, :log_date, :log_ip)""", p)

		return TinyResult(result)
	}

	fun list(dateFilter: Date?, NameFilter: String?, offset: Long, pageSize: Int): TinyResult<List<ActionLog>> {
		var optionSQL = " where 1"
		if(dateFilter != null) {
			optionSQL += " and  log_date = :log_date "
		}
		if(NameFilter != null) {
			optionSQL += " and  username like :username "
		}

		val p = hashMapOf(
				"offset" to offset,
				"pageSize" to pageSize
			)
		if(dateFilter != null) {
			p["log_date"] = dateFilter
		}
		if(NameFilter != null) {
			p["username"] = NameFilter
		}

		val result = db.queryForList("select * from action_log $optionSQL order by id desc limit :offset , :pageSize", p)
		return TinyResult.fromList(result, ActionLog::class)
	}

	fun getTotalCount(dateFilter: Date?, NameFilter: String?): TinyResult<Long> {
		var optionSQL = " where 1"
		if(dateFilter != null) {
			optionSQL += " and  log_date = :log_date "
		}
		if(NameFilter != null) {
			optionSQL += " and  username like :username "
		}

		val p: HashMap<String, Any> = hashMapOf()
		if(dateFilter != null) {
			p["log_date"] = dateFilter
		}
		if(NameFilter != null) {
			p["username"] = NameFilter
		}
		val result = db.queryForMap("select count(*) as cnt from action_log $optionSQL", p)
		if(result.ex != null) {
			return TinyResult("query error", result.ex.toString())
		}
		return TinyResult(null, result.data["cnt"] as Long)
	}
}