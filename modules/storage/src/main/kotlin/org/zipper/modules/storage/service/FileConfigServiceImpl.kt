package org.zipper.modules.storage.service

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convertOrNull
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.mybatis.core.selectAllList
import org.zipper.framework.mybatis.core.selectVoPage
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.modules.storage.client.FileClient
import org.zipper.modules.storage.client.FileClientFactory
import org.zipper.modules.storage.client.FileClientMasterDelegate
import org.zipper.modules.storage.constant.StorageConst
import org.zipper.modules.storage.domain.entity.SysFileConfigEntity
import org.zipper.modules.storage.domain.param.FileConfigPageParam
import org.zipper.modules.storage.domain.param.FileConfigSaveParam
import org.zipper.modules.storage.domain.vo.FileConfigVo
import org.zipper.modules.storage.enums.FileMessageConst
import org.zipper.modules.storage.mapper.FileConfigMapper

/**
 * 文件配置实现类
 *
 */
@Service
class FileConfigServiceImpl(
    private val fileClientFactory: FileClientFactory,
    private val fileConfigMapper: FileConfigMapper
) : FileConfigService {

    private val masterFileClient: FileClientMasterDelegate = FileClientMasterDelegate(::getOrCreateFileClient)

    @EventListener(ApplicationReadyEvent::class)
    fun init() {
        println("Spring启动完成")
        val dataList = fileConfigMapper.selectAllList()
        for (sysFileConfigEntity in dataList) {
            sysFileConfigEntity.syncRedis(false)
        }
    }

    override fun createConfig(fileConfigSaveParam: FileConfigSaveParam): Int {
        val entity = fileConfigSaveParam.toEntity()
        val row = fileConfigMapper.insert(entity)
        entity.syncRedis(false)
        return row
    }

    override fun updateConfig(fileConfigSaveParam: FileConfigSaveParam): Int {
        val entity = getFileConfig(fileConfigSaveParam.id)
        val configObject = fileConfigSaveParam.requireConfigObj()
        entity.config = configObject
        val row = fileConfigMapper.updateById(entity)
        if (row > 0) {
            fileClientFactory.updateFileClientIfExists(entity.requireConfigId, entity.requireStorage, entity.config!!)
            masterFileClient.checkMasterRelease(entity.requireConfigId)
            entity.syncRedis(true)
        }
        return row
    }

    override fun deleteConfig(ids: Array<Long>): Int {
        if (ids.isEmpty()) {
            return 0
        }
        val configList = fileConfigMapper.selectBatchIds(ids.toList())
        if (configList.isEmpty()) {
            throw ServiceException("数据空异常")
        }
        val allIds = mutableListOf<Long>()
        for (fileConfigEntity in configList) {
            if (fileConfigEntity.master) {
                throw FileMessageConst.UnableDeleteMasterConfigError
            }
            allIds.add(fileConfigEntity.requireConfigId)
        }

        val row = fileConfigMapper.deleteBatchIds(allIds)
        if (row > 0) {
            ids.map { StorageConst.CACHE_KEY_STORAGE_DOMAIN_FMT.format(it) }.forEach {
                RedisUtils.deleteObject(it)
            }
        }
        return row
    }

    override fun getConfig(id: Long): FileConfigVo? {
        return fileConfigMapper.selectById(id).convertOrNull()
    }

    override fun pageList(param: FileConfigPageParam): TableDataInfo<FileConfigVo> {
        val query = MybatisKt.ktQuery<SysFileConfigEntity>()
        val pageResult = fileConfigMapper.selectVoPage<SysFileConfigEntity, FileConfigVo>(param.build(), query)
        return TableDataInfo.build(pageResult)
    }

    override fun updateMaster(id: Long) {
        val entity = getFileConfig(id)
        fileConfigMapper.update(MybatisKt.ktUpdate<SysFileConfigEntity>().set(SysFileConfigEntity::master, false))
        entity.master = true
        fileConfigMapper.updateById(entity)
        masterFileClient.setValue(entity.requireConfigId)
    }

    override fun getFileClient(clientId: Long): FileClient {
        var client = fileClientFactory.getFileClient(clientId)
        if (client == null) {
            val entity = fileConfigMapper.selectById(clientId) ?: throw FileMessageConst.ClientNotFoundError
            if (entity.config == null) {
                throw FileMessageConst.ClientNotFoundError
            }
            client = fileClientFactory.createFileClient(entity.requireConfigId, entity.requireStorage, entity.config!!)
        }
        return client
    }

    override fun getMasterFileClient(): FileClient {
        return masterFileClient.getValue()
    }

    private fun getOrCreateFileClient(configId: Long): FileClient {
        val configEntity = if (configId == Long.MIN_VALUE) {
            requireMasterFileConfig()
        } else {
            getFileConfig(configId)
        }
        if (configEntity.config == null) {
            throw FileMessageConst.ClientNotFoundError
        }

        return fileClientFactory.createFileClient(configEntity.requireConfigId, configEntity.requireStorage, configEntity.config!!)
    }

    private fun getFileConfig(id: Long?): SysFileConfigEntity {
        return fileConfigMapper.selectById(id) ?: throw FileMessageConst.ConfigNotFoundError
    }

    private fun requireMasterFileConfig(): SysFileConfigEntity {
        return fileConfigMapper.selectOne(MybatisKt.ktQuery<SysFileConfigEntity>().eq(SysFileConfigEntity::master, true))
            ?: throw FileMessageConst.MasterConfigNotFoundError
    }

    /**
     * 保存文件存储的域名到redis中，获取文件记录的时候可以直接通过配置id取域名
     */
    private fun SysFileConfigEntity.syncRedis(reload: Boolean) {
        val redisCacheKey = redisCacheKey
        val updateEntity: SysFileConfigEntity? = if (reload) fileConfigMapper.selectById(id) else this
        if (updateEntity == null) {
            RedisUtils.deleteObject(redisCacheKey)
            return
        }
        val config = updateEntity.config
        if (config == null) {
            RedisUtils.deleteObject(redisCacheKey)
            return
        }
        RedisUtils.setCacheObject(redisCacheKey, config.getCustomDomain())
    }
}