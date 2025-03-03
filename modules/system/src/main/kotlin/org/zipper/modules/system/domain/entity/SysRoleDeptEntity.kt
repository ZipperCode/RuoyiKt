package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author Lion Li
 */
@TableName("sys_role_dept")
open class SysRoleDeptEntity {
    /**
     * 角色ID
     */
    @field:TableId(type = IdType.INPUT)
    var roleId: Long? = null

    /**
     * 部门ID
     */
    var deptId: Long? = null
}
