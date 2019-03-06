package release_manager.domain

import java.util.Date

class ActionLog(val map: HashMap<String, Any>) {
	var id: Long by map
	var userid: Long by map
	var username: String by map
	var action_name: String by map
	var return_message: String by map
	var log_date: Date by map
	var log_ip: String by map
}