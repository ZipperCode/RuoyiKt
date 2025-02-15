package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 用户和角色关联 sys_user_role
 *
 * @author Lion Li
 */
@TableName("sys_user_role")
open class SysUserRoleEntity {
    /**
     * 用户ID
     */
    @field:TableId(type = IdType.INPUT)
    var userId: Long? = null

    /**
     * 角色ID
     */
    var roleId: Long? = null
}
