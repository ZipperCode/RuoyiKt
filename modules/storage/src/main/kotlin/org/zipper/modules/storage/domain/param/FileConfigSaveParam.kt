package org.zipper.modules.storage.domain.param

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.zipper.common.core.utils.ValidatorUtils
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.starter.json.utils.JsonUtils
import org.zipper.modules.storage.client.FileClientConfig
import org.zipper.modules.storage.domain.entity.SysFileConfigEntity
import org.zipper.modules.storage.enums.FileStorageEnum
import java.io.Serializable

@Schema(description = "管理后台 - 文件配置创建/修改 Request VO")
class FileConfigSaveParam : Serializable {
    @Schema(description = "编号", example = "1")
    @field:NotNull(message = "编号不能为空", groups = [EditGroup::class])
    var id: Long? = null

    @Schema(description = "配置名", requiredMode = Schema.RequiredMode.REQUIRED, example = "S3 - 阿里云")
    @field:NotNull(message = "配置名不能为空")
    lateinit var name: String

    @Schema(description = "存储器，参见 FileStorageEnum 枚举类", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @field:NotNull(message = "存储器不能为空")
    var storage: Int? = null

    @Schema(description = "存储配置,配置是动态参数，所以使用 Map 接收", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotNull(message = "存储配置不能为空")
    lateinit var config: MutableMap<String, Any>

    @Schema(description = "备注", example = "我是备注")
    lateinit var remark: String

    fun requireConfigObj(): FileClientConfig {
        val fileStorageEnum = FileStorageEnum.getByStorage(storage!!)
        val configObj = JsonUtils.mapToObjectNonClassId(config, fileStorageEnum.configClass.java)
        ValidatorUtils.validate(configObj)
        return configObj
    }

    fun toEntity(): SysFileConfigEntity {
        val entity = SysFileConfigEntity()
        entity.name = name
        entity.storage = storage!!
        entity.config = requireConfigObj()
        entity.remark = remark
        return entity
    }
}