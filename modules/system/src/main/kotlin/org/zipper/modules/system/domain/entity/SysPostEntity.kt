package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity

/**
 * 岗位表 sys_post
 *
 * @author Lion Li
 */
@TableName("sys_post")
open class SysPostEntity : BaseMixinEntity() {
    /**
     * 岗位序号
     */
    @field:TableId(value = "post_id")
    var postId: Long? = null

    /**
     * 岗位编码
     */
    var postCode: String? = null

    /**
     * 岗位名称
     */
    var postName: String? = null

    /**
     * 岗位排序
     */
    var postSort: Int? = null

    /**
     * 状态（0正常 1停用）
     */
    var status: String? = null

    /**
     * 备注
     */
    var remark: String? = null
}
