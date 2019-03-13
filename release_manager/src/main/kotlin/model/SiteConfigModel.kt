package release_manager.model

import tiny.*
import release_manager.dao.*
import release_manager.domain.*

private val siteConfigDao = SiteConfigDao()

class SiteConfigModel {

	fun new(siteConfig: SiteConfig): TinyResult<Long> {
		return siteConfigDao.new(siteConfig)
	}

	fun list(offset: Long, pageSize: Long): TinyResult<List<SiteConfig>> {
		return siteConfigDao.list(offset, pageSize)
	}

	fun getById(id: Long): TinyResult<SiteConfig?> {
		return siteConfigDao.getById(id)
	}

	fun update(siteConfig: SiteConfig): TinyResult<Int> {
		return siteConfigDao.update(siteConfig)
	}

	fun delete(id: Long): TinyResult<Int> {
		return siteConfigDao.delete(id)
	}
}