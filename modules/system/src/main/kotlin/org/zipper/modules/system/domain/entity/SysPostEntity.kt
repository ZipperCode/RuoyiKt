package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.common.core.domain.mixin.sys.SysPostMixin

/**
 * 岗位表 sys_post
 *
 * @author Lion Li
 */
@TableName("sys_post")
open class SysPostEntity : BaseMixinEntity(), SysPostMixin {
    /**
     * 岗位序号
     */
    @field:TableId(value = "post_id")
    override var postId: Long? = null

    /**
     * 岗位编码
     */
    override var postCode: String? = null

    /**
     * 岗位名称
     */
    override var postName: String? = null

    /**
     * 岗位排序
     */
    override var postSort: Int? = null

    /**
     * 状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 备注
     */
    override var remark: String? = null
}
