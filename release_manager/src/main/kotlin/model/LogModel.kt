package release_manager.model

import tiny.*
import release_manager.dao.*
import release_manager.domain.*
import java.util.Date

private val actionLogDao = ActionLogDao()

class LogModel {
	
	fun add(userid: Long, username: String, action_name: String, return_message: String): TinyResult<Long> {
		return actionLogDao.add(userid, username, action_name, return_message)
	}

	fun list(dateFilter: Date?, NameFilter: String?, offset: Long = 0, pageSize: Int = 10): TinyResult<List<ActionLog>> {
		return actionLogDao.list(dateFilter, NameFilter, offset, pageSize)
	}

	fun getTotalCount(dateFilter: Date?, NameFilter: String?): TinyResult<Long>{
		return actionLogDao.getTotalCount(dateFilter, NameFilter)
	}
}