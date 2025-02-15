package org.zipper.modules.system.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.zipper.common.core.constant.UserConstants
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysRoleEntity
import org.zipper.modules.system.domain.mixin.SysRoleMixin

/**
 * 角色信息业务对象 dto
 *
 */
@AutoMapper(target = SysRoleEntity::class, reverseConvertGenerate = false)
class SysRoleParam : BaseMixinVo(), SysRoleMixin {
    /**
     * 角色ID
     */
    override var roleId: Long? = null

    /**
     * 角色名称
     */
    @field:NotBlank(message = "角色名称不能为空")
    @field:Size(
        min = 0,
        max = 30,
        message = "角色名称长度不能超过{max}个字符"
    )
    override var roleName: String? = null

    /**
     * 角色权限字符串
     */
    @field:NotBlank(message = "角色权限字符串不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "权限字符长度不能超过{max}个字符"
    )
    override var roleKey: String? = null

    /**
     * 显示顺序
     */
    @field:NotNull(message = "显示顺序不能为空")
    override var roleSort: Int? = null

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    override var dataScope: String? = null

    /**
     * 菜单树选择项是否关联显示
     */
    override var menuCheckStrictly: Boolean? = null

    /**
     * 部门树选择项是否关联显示
     */
    override var deptCheckStrictly: Boolean? = null

    /**
     * 角色状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 备注
     */
    override var remark: String? = null

    /**
     * 菜单组
     */
    var menuIds: Array<Long> = emptyArray()

    /**
     * 部门组（数据权限）
     */
    var deptIds: Array<Long> = emptyArray()


    val isSuperAdmin: Boolean
        get() = UserConstants.SUPER_ADMIN_ID == this.roleId
}
