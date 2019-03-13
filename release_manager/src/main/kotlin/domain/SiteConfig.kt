package release_manager.domain


class SiteConfig(val map: HashMap<String, Any>) {
	var id: Long by map
	var sitename: String by map
	var base_dir: String by map
	var username: String by map
	var get_current_branch_command: String by map
	var update_command: String by map
	var generate_command: String by map
	var test_release_command: String by map
	var release_command: String by map
	var cache_dir: String by map
	var cache_exclude_dir: String by map
	var cache_urls: String by map
}