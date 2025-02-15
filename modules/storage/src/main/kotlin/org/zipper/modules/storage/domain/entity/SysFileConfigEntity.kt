package org.zipper.modules.storage.domain.entity

import com.baomidou.mybatisplus.annotation.*
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.framework.mybatis.core.domain.LogicDeleteMixin
import org.zipper.framework.starter.json.utils.JsonUtils
import org.zipper.framework.starter.json.utils.JsonUtils.createReference
import org.zipper.modules.storage.client.FileClientConfig
import org.zipper.modules.storage.client.db.DataBaseFileClientConfig
import org.zipper.modules.storage.client.local.LocalFileClientConfig
import org.zipper.modules.storage.client.s3.S3FileClientConfig
import org.zipper.modules.storage.constant.StorageConst
import org.zipper.modules.storage.domain.mixin.FileConfigMixin
import org.zipper.modules.storage.enums.FileStorageEnum

@TableName("sys_file_config", autoResultMap = true)
class SysFileConfigEntity : BaseMixinEntity(), LogicDeleteMixin, FileConfigMixin {
    /**
     * 配置编号，数据库自增
     */
    @field:TableId(value = "config_id", type = IdType.AUTO)
    override var id: Long? = null

    val requireConfigId: Long get() = id!!

    val redisCacheKey: String get()  = StorageConst.CACHE_KEY_STORAGE_DOMAIN_FMT.format(id)

    /**
     * 配置名
     */
    override var name: String = ""

    /**
     * 存储器
     *
     * 枚举 [FileStorageEnum]
     */
    override var storage: Int = FileStorageEnum.LOCAL.storage

    val requireStorage: Int get() = storage

    /**
     * 备注
     */
    override var remark: String? = null

    /**
     * 是否为主配置
     *
     * 由于我们可以配置多个文件配置，默认情况下，使用主配置进行文件的上传
     */
    override var master: Boolean = false

    @TableField(typeHandler = FileClientConfigTypeHandler::class)
    override var config: FileClientConfig? = null

    @field:TableLogic
    override var deleted: String? = LogicDeleteMixin.NORMAL

    class FileClientConfigTypeHandler : AbstractJsonTypeHandler<FileClientConfig?>() {

        override fun parse(json: String?): FileClientConfig? {
            if (json.isNullOrEmpty()) {
                return null
            }
            return when (JsonUtils.parseObject(json, "@class", String::class.java)) {
                DataBaseFileClientConfig::class.java.name -> {
                    JsonUtils.parseObjectReference(json, createReference<DataBaseFileClientConfig>())
                }

                LocalFileClientConfig::class.java.name -> {
                    JsonUtils.parseObjectReference(json, createReference<LocalFileClientConfig>())
                }

                S3FileClientConfig::class.java.name -> {
                    JsonUtils.parseObjectReference(json, createReference<S3FileClientConfig>())
                }

                else -> {
                    null
                }
            }
        }

        override fun toJson(obj: FileClientConfig?): String? = JsonUtils.toJsonString(obj)
    }
}