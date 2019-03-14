package release_manager.controller

import tiny.annotation.Controller
import release_manager.library.Utils
import release_manager.model.*
import tiny.*
import tiny.lib.*

private val userModel = UserModel()

@Controller
class TestController : BaseController() {

	fun md5Action(): Any {
		return userModel.getPasswordMd5("pass13579")
	}

	fun aAction(): Any {
		val result = LogModel.list(null,null)
		if(result.error()){
			println("err: " + result.cause)
		}else{
			println("data: ")
			DebugUtil.inspect(result.data())
			val oneLog = result.data()[0]
			DebugUtil.inspect(oneLog.map)
		}
		val result2 = LogModel.getTotalCount(null,null)
		println(result2.data())
		return result.toString() + " | " + result2.data().toString()
	}
}