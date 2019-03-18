package release_manager

import tiny.*
import tiny.lib.*
import tiny.lib.db.*
import tiny.annotation.*
import javax.servlet.annotation.WebListener
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.util.thread.QueuedThreadPool

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

	/*run script*/
	if(!app.script.isEmpty()){
		TinyScript.run(app.env, app.name, app.script)
	}

	/*start web server*/
	if(app.script.isEmpty()) {
		runJettyWithFatJar()
	}
}


fun runJettyWithFatJar(){
	val port = System.getProperty("tiny.app.port")?.toIntOrNull() ?: 8080
	val maxthreads = System.getProperty("tiny.app.maxthreads")?.toIntOrNull() ?: 200
	val minthreads = System.getProperty("tiny.app.minthreads")?.toIntOrNull() ?: 10

	val threadPool = QueuedThreadPool(maxthreads, minthreads)
	val nThreads = Runtime.getRuntime().availableProcessors()
	val server = Server(threadPool)
	server.setStopAtShutdown(true)
	val http = ServerConnector(server, nThreads, nThreads)
	http.setPort(port)
	server.addConnector(http)

	val context = WebAppContext()
	context.setContextPath("/")
	context.setResourceBase("./web")
	context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*")
	
	context.setConfigurations(arrayOf(
		org.eclipse.jetty.annotations.AnnotationConfiguration(),
		org.eclipse.jetty.webapp.WebInfConfiguration()
	))
	server.setHandler(context)

	server.start()
	server.join()
}


