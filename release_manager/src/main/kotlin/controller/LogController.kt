package release_manager.controller

import tiny.annotation.Controller
import release_manager.model.*
import release_manager.domain.*
import tiny.*
import release_manager.library.Paging
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import java.util.Date
import java.text.SimpleDateFormat
import io.reactivex.functions.BiFunction

private val userModel = UserModel()
private val logModel = LogModel()

@Controller
class LogController : BaseController() {

	override fun before() {
		super.before()
		userModel.hasLoggedin(true)
	}

	fun indexAction(): Any {
		val username = ctx.params["username"]
		val date = ctx.params["date"]
		var baseUrl = "/log/"
		
		if(username != "" || date != "") {
			view["username"] = username
			view["date"] = date
			baseUrl = "/log/?username=" + username + "&date=" + date
		}
		var page = ctx.params.getLong("page")
		if(page < 1L) {
			page = 1
		}
		val pageSize = 10L
		val offset = (page -1) * pageSize
		var dateFilter: Date? = null
		var nameFilter: String? = null
		if(date != "") {
			val sdf = SimpleDateFormat("yyyy-MM-dd")
			try{
				dateFilter = sdf.parse(date)
			}catch(e: Throwable) {
				//pass
			}
		}
		if(username != "") {
			nameFilter = username
		}

		val logListSingle = Single.fromCallable(Callable<TinyResult<List<ActionLog>>> {
				logModel.list(dateFilter, nameFilter, offset, pageSize)
			}).subscribeOn(Schedulers.io())

		val logTotalSingle = Single.fromCallable(Callable<TinyResult<Long>> {
				logModel.getTotalCount(dateFilter, nameFilter)
			}).subscribeOn(Schedulers.io())

		val mergeResult = BiFunction<TinyResult<List<ActionLog>>, TinyResult<Long>, Unit> {
			trLogList, trLogTotal ->
			if(trLogList.error()) {
				throw LogControllerException("logList error: " + trLogList.cause())
			}
			if(trLogTotal.error()) {
				throw LogControllerException("logTotal error: " + trLogTotal.cause())
			}
			val total = trLogTotal.data()
			val pageStr = Paging.page(total, baseUrl, page, pageSize)
			view["pageStr"] = pageStr
			view["logList"] = trLogList.data()
		}

		Single.zip(logListSingle, logTotalSingle, mergeResult)
			.blockingGet()
		return ""
	}
}

private class LogControllerException(message: String?) : Throwable(message)