package org.zipper.common.core.domain.mixin.base

import java.time.LocalDateTime

interface CreatorMixin : IMixin {
    /**
     * 创建者
     */
    var createBy: Long?

    /**
     * 创建时间
     */
    var createTime: LocalDateTime?
}