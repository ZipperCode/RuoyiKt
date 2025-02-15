package org.zipper.modules.system.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysDeptEntity
import org.zipper.modules.system.domain.mixin.SysDeptMixin

/**
 * 部门业务 dto
 *
 */
@AutoMapper(target = SysDeptEntity::class, reverseConvertGenerate = false)
class SysDeptParam : BaseMixinVo(), SysDeptMixin {
    /**
     * 部门id
     */
    override var deptId: Long? = null

    /**
     * 父部门ID
     */
    override var parentId: Long? = null

    /**
     * 部门名称
     */
    @field:NotBlank(message = "部门名称不能为空")
    @field:Size(
        min = 0,
        max = 30,
        message = "部门名称长度不能超过{max}个字符"
    )
    override var deptName: String? = null

    /**
     * 显示顺序
     */
    @field:NotNull(message = "显示顺序不能为空")
    override var orderNum: Int? = null

    /**
     * 负责人
     */
    override var leader: Long? = null

    /**
     * 联系电话
     */
    @field:Size(min = 0, max = 11, message = "联系电话长度不能超过{max}个字符")
    override var phone: String? = null

    /**
     * 邮箱
     */
    @field:Email(message = "邮箱格式不正确")
    @field:Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    override var email: String? = null

    /**
     * 部门状态（0正常 1停用）
     */
    override var status: String? = null
}
