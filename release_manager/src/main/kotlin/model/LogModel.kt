package release_manager.model

import tiny.*
import release_manager.dao.*
import release_manager.domain.*
import java.util.Date

/*for demo, can use single instance(object) for thread-safe classes */
object LogModel {
	
	fun add(userid: Long, username: String, action_name: String, return_message: String): TinyResult<Long> {
		return ActionLogDao.add(userid, username, action_name, return_message)
	}

	fun list(dateFilter: Date?, nameFilter: String?, offset: Long = 0, pageSize: Long = 10): TinyResult<List<ActionLog>> {
		return ActionLogDao.list(dateFilter, nameFilter, offset, pageSize)
	}

	fun getTotalCount(dateFilter: Date?, nameFilter: String?): TinyResult<Long>{
		return ActionLogDao.getTotalCount(dateFilter, nameFilter)
	}
}