package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.common.core.constant.UserConstants
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysRoleEntity
import org.zipper.modules.system.domain.mixin.SysRoleMixin
import org.zipper.modules.system.excel.ExcelDictConvert
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 角色信息视图对象 sys_role
 *
 * @author Michelle.Chung
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleEntity::class)
class SysRoleVo : BaseMixinVo(), SysRoleMixin, Serializable {
    /**
     * 角色ID
     */
    @field:ExcelProperty(value = ["角色序号"])
    override var roleId: Long? = null

    /**
     * 角色名称
     */
    @field:ExcelProperty(value = ["角色名称"])
    override var roleName: String? = null

    /**
     * 角色权限字符串
     */
    @field:ExcelProperty(value = ["角色权限"])
    override var roleKey: String? = null

    /**
     * 显示顺序
     */
    @field:ExcelProperty(value = ["角色排序"])
    override var roleSort: Int? = null

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    @field:ExcelProperty(value = ["数据范围"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限,5=仅本人数据权限")
    override var dataScope: String? = null

    /**
     * 菜单树选择项是否关联显示
     */
    @field:ExcelProperty(value = ["菜单树选择项是否关联显示"])
    override var menuCheckStrictly: Boolean? = null

    /**
     * 部门树选择项是否关联显示
     */
    @field:ExcelProperty(value = ["部门树选择项是否关联显示"])
    override var deptCheckStrictly: Boolean? = null

    /**
     * 角色状态（0正常 1停用）
     */
    @field:ExcelProperty(value = ["角色状态"], converter = ExcelDictConvert::class)
    @ExcelDictFormat(dictType = "sys_normal_disable")
    override var status: String? = null

    /**
     * 备注
     */
    @field:ExcelProperty(value = ["备注"])
    override var remark: String? = null

    /**
     * 创建时间
     */
    @field:ExcelProperty(value = ["创建时间"])
    override var createTime: LocalDateTime? = null

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    var flag = false

    val isSuperAdmin: Boolean
        get() = UserConstants.SUPER_ADMIN_ID.equals(this.roleId)

}
