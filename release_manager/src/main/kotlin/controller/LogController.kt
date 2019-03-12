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
		
		view["username"] = ""
		view["date"] = ""
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
			val sdf = SimpleDateFormat("MM/dd/yyyy")
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

		val mergeResult = BiFunction<TinyResult<List<ActionLog>>, TinyResult<Long>, Map<String, Any>> {
			trLogList, trLogTotal ->
			if(trLogList.error()) {
				throw LogControllerException("logList error: " + trLogList.cause())
			}
			if(trLogTotal.error()) {
				throw LogControllerException("logTotal error: " + trLogTotal.cause())
			}
			
			return@BiFunction mapOf(
					"total" to trLogTotal.data(),
					"list" to trLogList.data()
				)
		}

		val merged = Single.zip(logListSingle, logTotalSingle, mergeResult)
			.blockingGet()

		view["logList"] = merged["list"] as Any
		val pageStr = Paging.page(merged["total"] as Long, baseUrl, page, pageSize)
		view["pageStr"] = pageStr

		return view.render("log/index")
	}
}

private class LogControllerException(message: String?) : Throwable(message)