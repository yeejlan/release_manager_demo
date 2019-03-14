package release_manager.service

import tiny.annotation.Controller
import release_manager.model.*
import tiny.*
import java.io.PrintWriter
import java.io.File

private val commandMapping = mapOf(
		"getCurrentBranch" to "get_current_branch_command",
		"update" to "update_command",
		"generate" to "generate_command",
		"testRelease" to "test_release_command",
		"release" to "release_command"
	)
private val COMMAND_COLOR = "#3A87AD"
private val SUCCESS_COLOR = "#468847"
private val FAILURE_COLOR = "#894A48"

private val siteConfigModel = SiteConfigModel()

class ReleaseService {
	private val _runResult = StringBuilder("")
	private lateinit var _writer: PrintWriter

	fun runCommand(writer: PrintWriter, siteId: Long, command: String): Unit {
		_writer = writer
		if(command == "") {
			return
		}
		if(siteId < 1) {
			_print("invalid siteId")
			return
		}
		if(!commandMapping.containsKey(command)) {
			_print("invalid command")
			return
		}
		var siteInfo = siteConfigModel.getById(siteId).data()
		if(siteInfo == null) {
			_print("Site not found, siteId = " + siteId)
			return
		}

		val docBegin = """<!DOCTYPE html><html><head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>file content</title></head><body>"""
		val scrollBegin = """<script>var scroll_to_bottom = function() { var height = document.body.scrollHeight; window.scrollTo(0, height) }, timer = setInterval(scroll_to_bottom, 100);</script>"""
		writer.write(docBegin)
		writer.write(scrollBegin)

		val cmdKey = commandMapping[command] as String
		var cmdStr = siteInfo.map[cmdKey] as String
		var baseDir = siteInfo.map["base_dir"] as String
		cmdStr = cmdStr.replace("\r\n", "\n")
		cmdStr = cmdStr.replace("\r", "\n")
		val commands = cmdStr.split('\n')
		var bytesRead: Int
		for(oneCmd in commands) {
			_print("""<br /><strong>Command Executed:</strong><br />
				<span style="color:""" + COMMAND_COLOR + "\">" + oneCmd +
				"</span><br /><br /><strong>Execution Result:</strong>")

			try{
				val p = ProcessBuilder(oneCmd.split(' ')).directory(File(baseDir)).start()
				val out = p.getInputStream()
				val err = p.getErrorStream()

				_print("<br /><br /><span style=\"color:" + SUCCESS_COLOR + ";\">");
				val outBuffer = ByteArray(1024)
				do{
					bytesRead = out.read(outBuffer, 0, outBuffer.size)
					val str = String(outBuffer)
					_print(str)

				}while (bytesRead == outBuffer.size)
				_print("</span>")

				_print("<span style=\"color:" + FAILURE_COLOR + ";\">");
				val errBuffer = ByteArray(1024)
				do{
					bytesRead = err.read(errBuffer, 0, errBuffer.size)
					val str = String(errBuffer)
					_print(str)

				}while (bytesRead == errBuffer.size)
				_print("</span><br />")
			}catch(e: Throwable) {
				e.printStackTrace(writer)
			}
		}
		
		val scrollEnd = "<script>clearInterval(timer); setTimeout(scroll_to_bottom, 500);</script>";
		val docEnd = "</body></html>"
		writer.write(scrollEnd)
		writer.write(docEnd)
		
		//log
		val session = TinyRouter.ctx().session
		LogModel.add(
			session["uid"] as Long, 
			session["username"] as String,
			command,
			_runResult.toString()
		)
	}

	fun _print(message: String) {
		var msg = message.replace("\r\n", "\n")
		msg = msg.replace("\r", "\n")
		msg = msg.replace("\n", "<br />\n")
		_writer.write(msg)
		_writer.flush()
		_runResult.append(msg)
	}
}
