package release_manager

import tiny.*
import tiny.lib.*
import tiny.lib.db.*
import tiny.annotation.*
import javax.servlet.annotation.WebListener

@TinyApplication
class ReleaseManager : TinyBootstrap {
	val name = "release_manager"
	val env = System.getProperty("tiny.app.env") ?: "production"
	val script = System.getProperty("tiny.app.script") ?: ""

	override fun bootstrap() {
		TinyApp.init(env, name)
	}
}

fun main(args: Array<String>) {

	val app = ReleaseManager()
	app.bootstrap()

	try{
		//test()

		if(!app.script.isEmpty()){
			TinyScript.run(app.env, app.name, app.script)
		}else{
			TinyApp.runJetty()
		}
	}finally{
		TinyApp.shutdown()
	}


}


fun test(){
	
}


