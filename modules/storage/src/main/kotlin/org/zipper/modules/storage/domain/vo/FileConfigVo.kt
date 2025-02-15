package org.zipper.modules.storage.domain.vo

import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.storage.client.FileClientConfig
import org.zipper.modules.storage.domain.entity.SysFileConfigEntity
import org.zipper.modules.storage.domain.mixin.FileConfigMixin
import org.zipper.modules.storage.enums.FileStorageEnum

@JsonIgnoreType
@AutoMapper(target = SysFileConfigEntity::class)
class FileConfigVo : BaseMixinVo(), FileConfigMixin {

    /**
     * 配置编号，数据库自增
     */
    override var id: Long? = null

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

    /**
     * 配置
     */
    @field:JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    override var config: FileClientConfig? = null
}