package org.zipper.modules.system.domain.mixin

interface SysRoleMixin {
    /**
     * 角色ID
     */
    var roleId: Long?

    /**
     * 角色名称
     */
    var roleName: String?

    /**
     * 角色权限
     */
    var roleKey: String?

    /**
     * 角色排序
     */
    var roleSort: Int?

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）
     */
    var dataScope: String?

    /**
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    var menuCheckStrictly: Boolean?

    /**
     * 部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示 ）
     */
    var deptCheckStrictly: Boolean?

    /**
     * 角色状态（0正常 1停用）
     */
    var status: String?

    /**
     * 备注
     */
    var remark: String?
}