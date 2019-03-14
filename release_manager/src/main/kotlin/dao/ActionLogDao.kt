package release_manager.dao

import tiny.*
import tiny.lib.*
import release_manager.domain.*
import java.util.Date
import release_manager.library.Utils
import java.text.SimpleDateFormat

private val db = TinyRegistry["db.release_manager"] as TinyJdbc

/*for demo, can use single instance(object) for thread-safe classes */
object ActionLogDao {

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

	fun list(dateFilter: Date?, nameFilter: String?, offset: Long, pageSize: Long): TinyResult<List<ActionLog>> {
		var optionSQL = " where 1"
		if(dateFilter != null) {
			optionSQL += " and  log_date = :log_date "
		}
		if(nameFilter != null) {
			optionSQL += " and  username like :username "
		}

		val p = hashMapOf<String, Any>(
				"offset" to offset,
				"pageSize" to pageSize
			)
		if(dateFilter != null) {
			val sdf = SimpleDateFormat("yyyy-MM-dd")
			p["log_date"] = sdf.format(dateFilter)
		}
		if(nameFilter != null) {
			p["username"] = nameFilter
		}

		val result = db.queryForList("select * from action_log $optionSQL order by id desc limit :offset , :pageSize", p)
		return TinyResult.fromList(result, ActionLog::class)
	}

	fun getTotalCount(dateFilter: Date?, nameFilter: String?): TinyResult<Long> {
		var optionSQL = " where 1"
		if(dateFilter != null) {
			optionSQL += " and  log_date = :log_date "
		}
		if(nameFilter != null) {
			optionSQL += " and  username like :username "
		}

		val p: HashMap<String, Any> = hashMapOf()
		if(dateFilter != null) {
			val sdf = SimpleDateFormat("yyyy-MM-dd")
			p["log_date"] = sdf.format(dateFilter)
		}
		if(nameFilter != null) {
			p["username"] = nameFilter
		}
		val result = db.queryForMap("select count(*) as cnt from action_log $optionSQL", p)
		if(result.ex != null) {
			return TinyResult("query error", result.ex.toString())
		}
		return TinyResult(null, result.data["cnt"] as Long)
	}
}