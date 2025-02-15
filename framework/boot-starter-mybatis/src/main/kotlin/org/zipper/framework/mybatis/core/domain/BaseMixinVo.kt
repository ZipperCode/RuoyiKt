package org.zipper.framework.mybatis.core.domain

import java.io.Serializable
import java.time.LocalDateTime

abstract class BaseMixinVo : CreatorMixin, UpdaterMixin, Serializable {
    /**
     * 创建者
     */
    override var createBy: Long? = null

    /**
     * 创建时间
     */
    override var createTime: LocalDateTime? = null

    /**
     * 更新者
     */
    override var updateBy: Long? = null

    /**
     * 更新时间
     */
    override var updateTime: LocalDateTime? = null

    var params: Map<String, Any> = HashMap()
}