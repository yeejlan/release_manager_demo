package release_manager.library

import java.math.BigInteger
import java.security.MessageDigest
import java.nio.charset.StandardCharsets
import tiny.*

object Utils {

	fun md5(text: String): String {
		val md5 = MessageDigest.getInstance("MD5")
		md5.update(StandardCharsets.UTF_8.encode(text))
		return String.format("%032x", BigInteger(1, md5.digest()))
	}

	fun getClientIp(): String {
		val request = TinyRouter.ctx().request
		var ip: String? = request.getHeader("X_FORWARDED_FOR")
		if(ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
			ip = request.getHeader("Proxy-Client-IP")
		}
		if(ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
			ip = request.getHeader("WL-Proxy-Client-IP")
		}
		if(ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
			ip = request.getHeader("HTTP_CLIENT_IP")
		}
		if(ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR")
		}
		if(ip == null || ip.isEmpty() || "unknown" == ip.toLowerCase()) {
			ip = request.getRemoteAddr()
		}
		if(ip != null && ip.contains(",")){
			return ip.split(",")[0]
		}
		return ip ?: ""
	}
}