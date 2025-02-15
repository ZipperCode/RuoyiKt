package org.zipper.modules.storage.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.stereotype.Service
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.betweenIfPresent
import org.zipper.framework.mybatis.core.likeIfPresent
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.modules.storage.constant.StorageConst
import org.zipper.modules.storage.domain.bo.FileCreateBo
import org.zipper.modules.storage.domain.entity.SysFileRecordEntity
import org.zipper.modules.storage.domain.param.FileRecordPageParam
import org.zipper.modules.storage.domain.vo.FileRecordVo
import org.zipper.modules.storage.mapper.FileRecordMapper

@Service
class FileRecordServiceImpl(
    private val fileRecordMapper: FileRecordMapper
) : FileRecordService {
    override fun create(fileCreateBo: FileCreateBo): FileRecordVo {
        val entity = SysFileRecordEntity()
        fileCreateBo.convert(entity)
        fileRecordMapper.insert(entity)
        return entity.convert()
    }

    override fun queryByIds(recordIds: Array<Long>): List<FileRecordVo> {
        val list =  fileRecordMapper.selectList(
            MybatisKt.ktQuery<SysFileRecordEntity>()
                .`in`(SysFileRecordEntity::id, *recordIds)
        )
        return list.mapConcatDomain()
    }

    override fun delete(recordIds: Array<Long>): List<SysFileRecordEntity> {
        val deleteRecordList = fileRecordMapper.selectList(
            MybatisKt.ktQuery<SysFileRecordEntity>()
                .`in`(SysFileRecordEntity::id, *recordIds)
        )
        fileRecordMapper.deleteBatchIds(recordIds.toList())
        return deleteRecordList
    }

    override fun pageList(param: FileRecordPageParam): TableDataInfo<FileRecordVo> {
        val queryPage = param.build<SysFileRecordEntity>()
        val pageList = fileRecordMapper.selectList(
            queryPage, MybatisKt.ktQuery<SysFileRecordEntity>()
                .likeIfPresent(SysFileRecordEntity::path, param.path)
                .likeIfPresent(SysFileRecordEntity::mimeType, param.type)
                .betweenIfPresent(SysFileRecordEntity::createTime, param.createTime)
                .orderByDesc(SysFileRecordEntity::id)
        )
        val pageResult = Page<FileRecordVo>(queryPage.current, queryPage.size, queryPage.total)
        pageResult.setRecords(pageList.mapConcatDomain())
        return TableDataInfo.build(pageResult)
    }


    private fun List<SysFileRecordEntity>.mapConcatDomain(): List<FileRecordVo> {
        forEach {
            val domainKey = StorageConst.CACHE_KEY_STORAGE_DOMAIN_FMT.format(it.configId)
            var domain = RedisUtils.getCacheObject<String>(domainKey)
            if (domain?.endsWith("/") == true) {
                domain = domain.substring(0, domain.length - 1)
            }
            if (it.url?.startsWith("/") == true) {
                it.url = it.url?.substring(1)
            }
            it.url = "%s/%s".format(domain, it.url)
        }
        return this.convertList()
    }
}