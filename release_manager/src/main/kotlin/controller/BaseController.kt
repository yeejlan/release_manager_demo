package release_manager.controller

import tiny.TinyController
import tiny.*

open class BaseController : TinyController() {

	val currController = TinyRouter.currController()
	val currAction =  TinyRouter.currAction()

	override fun before() {
		
		super.before()
		
		view["currController"] = currController
		view["session"] = ctx.session

		//no cache
		ctx.response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate")

		ctx.session["keep-alive"] = System.currentTimeMillis()	
	}

}