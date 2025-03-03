package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author Lion Li
 */
@TableName("sys_user_post")
open class SysUserPostEntity {
    /**
     * 用户ID
     */
    @field:TableId(type = IdType.INPUT)
    var userId: Long? = null

    /**
     * 岗位ID
     */
    var postId: Long? = null
}
