package release_manager.dao

import tiny.*
import tiny.lib.*
import release_manager.domain.*
import release_manager.library.Utils

private val db: TinyJdbc = TinyRegistry["db.release_manager"]

class SiteConfigDao {

	fun new(siteConfig: SiteConfig): TinyResult<Long> {

		if(siteConfig.sitename == "") {
			return TinyResult("bad sitename", null as Long?)
		}
		val p = siteConfig.map

		val result = db.insert("""insert into siteconfig 
			(`sitename`,`base_dir`,`get_current_branch_command`,`update_command`,
			generate_command,`test_release_command`,`release_command`,`cache_dir`,
			`cache_exclude_dir`,`cache_urls`) values 
			(:sitename,:base_dir,:get_current_branch_command,:update_command,
			:generate_command,:test_release_command,:release_command,:cache_dir,
			:cache_exclude_dir,:cache_urls)""".trimMargin(), p)

		return TinyResult(result)
	}

	fun list(offset: Long, pageSize: Long): TinyResult<List<SiteConfig>> {

		val p = hashMapOf<String, Any>(
				"offset" to offset,
				"pageSize" to pageSize
			)

		val result = db.queryForList("select * from siteconfig limit :offset , :pageSize", p)
		return TinyResult.fromList(result, SiteConfig::class)
	}

	fun getById(id: Long): TinyResult<SiteConfig?> {
		if(id < 1) {
			return TinyResult("invalid id", null as SiteConfig?)
		}
		val p = hashMapOf<String, Any>(
				"id" to id
			)
		val result = db.queryForMap("select * from siteconfig where id = :id", p)
		return TinyResult.fromMap(result, SiteConfig::class)
	}

	fun update(siteConfig: SiteConfig): TinyResult<Int> {

		val p = siteConfig.map

		val result = db.update("""update siteconfig set 
			`sitename` = :sitename, 
			`base_dir` = :base_dir, 
			`get_current_branch_command` = :get_current_branch_command, 
			`update_command` = :update_command, 
			`generate_command` = :generate_command, 
			`test_release_command` = :test_release_command, 
			`release_command` = :release_command,  
			`cache_dir` = :cache_dir,  
			`cache_exclude_dir` = :cache_exclude_dir,  
			`cache_urls` = :cache_urls 
			 WHERE id = :id""".trimMargin(), p)

		return TinyResult(result)	
	}

	fun delete(id: Long): TinyResult<Int> {
		if(id < 1) {
			return TinyResult("invalid id", null as Int?)
		}
		val p = mapOf(
				"id" to id
			)
		val result = db.update("delete from siteconfig where id = :id", p)
		return TinyResult(result)
	}

}