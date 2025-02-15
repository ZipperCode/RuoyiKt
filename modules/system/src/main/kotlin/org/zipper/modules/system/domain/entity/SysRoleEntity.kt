package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity

/**
 * 角色表 sys_role
 *
 * @author Lion Li
 */
@TableName("sys_role")
open class SysRoleEntity : BaseMixinEntity() {

    /**
     * 角色ID
     */
    @field:TableId(value = "role_id")
    var roleId: Long? = 0

    /**
     * 角色名称
     */
    var roleName: String? = null

    /**
     * 角色权限
     */
    var roleKey: String? = null

    /**
     * 角色排序
     */
    var roleSort: Int? = null

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    var dataScope: String? = null

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    var menuCheckStrictly: Boolean? = null

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    var deptCheckStrictly: Boolean? = null

    /**
     * 角色状态（0正常 1停用）
     */
    var status: String? = null

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @field:TableLogic
    var delFlag: String? = null

    /**
     * 备注
     */
    var remark: String? = null
}
