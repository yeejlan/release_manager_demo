package release_manager.domain

class User(val map: HashMap<String, Any>) {
	var id: Long by map
	var username: String by map
	var password: String by map
	var role: String by map
}