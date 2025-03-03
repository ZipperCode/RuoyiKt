package org.zipper.framework.mybatis.core.domain

import java.io.Serializable
import java.time.LocalDateTime

interface UpdaterMixin : IMixin {
    /**
     * 创建者
     */
    var updateBy: Long?

    /**
     * 创建时间
     */
    var updateTime: LocalDateTime?
}