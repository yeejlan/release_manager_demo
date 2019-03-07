package release_manager.library

import tiny.*

object Paging {
	fun page(total: Long, baseUrlStr: String, currentPage: Long, pageSize: Long): String{

		val pages = total / pageSize + if(total % pageSize == 0L) 0 else 1
		var currPage = currentPage
		var prev = 1L
		var next = pages
		var last = pages
		var baseUrl = baseUrlStr
		
		if(pages < 2) {
			return ""
		}
		if(currPage > pages) {
			currPage = pages
		}
		if(currPage < 1) {
			currPage = 1
		}
		if(currPage > 1) { //page prev
			prev = currPage -1
		}

		if(currPage < pages) { //page next
			next = currPage + 1
		}

		baseUrl = baseUrl.replace("\\?page=([0-9]+)&".toRegex(), "?")
		baseUrl = baseUrl.replace("\\?page=([0-9]+)".toRegex(), "")
		baseUrl = baseUrl.replace("&page=([0-9]+)&".toRegex(), "")

		var pageParam = "page="
		val params = TinyRouter.ctx().params
		if(baseUrl.endsWith('/') || (params["page"] != "" && params.getMap().size == 1)) {
			pageParam = "?" + pageParam
		}else {
			if(!baseUrl.endsWith('&')) {
				pageParam = "&" + pageParam
			}
		}

		// first page & page previous
		var str = ""
		if(currPage == 1L) {
			str += """<li><a href="#" title="first">&lt;&lt;</a></li>"""
			str += """<li><a href="#" title="previous">&lt;</a></li>"""
		}else {
			str += "<li><a href=\"" + baseUrl + pageParam + """1"  title="first">&lt;&lt;</a></li>"""
			str += "<li><a href=\"" + baseUrl + pageParam + prev + """ " title="previous">&lt;</a></li>"""
		}

		var start_pos = 0L
		if(currPage > 5L) {
			start_pos = currPage -5
		}
		var end_pos = pages
		if(currPage < pages -5) {
			end_pos = currPage + 5
		}

		for(c in start_pos until end_pos) {
			val pageNumber = c + 1
			if(currPage == pageNumber) {
				str += """<li class="active"><a href="#">""" + pageNumber + "</a></li>"
			}else {
				str += "<li><a href=\"" + baseUrl + pageParam + pageNumber + "\">" + pageNumber + "</a></li>"
			}
		}

		// last page
		if(currPage == last) {
			str += """<li><a href="#" title="next">&gt;</a></li>"""
			str += """<li><a href="#"  title="last">&gt;&gt;</a></li>"""
		}else {
			str += "<li><a href=\"" + baseUrl + pageParam + next + """ " title="next">&gt;</a></li>"""
			str += "<li><a href=\"" + baseUrl + pageParam + last + """ " title="last">&gt;&gt;</a></li>"""
		}

		return str
	}
}