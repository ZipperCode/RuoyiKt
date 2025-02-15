package org.zipper.framework.mybatis.core.domain

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.io.Serializable
import java.time.LocalDateTime

abstract class BaseMixinEntity : CreatorMixin, UpdaterMixin, Serializable {
    /**
     * 创建者
     */
    @field:TableField(fill = FieldFill.INSERT)
    override var createBy: Long? = null

    /**
     * 创建时间
     */
    @field:TableField(fill = FieldFill.INSERT)
    override var createTime: LocalDateTime? = null

    /**
     * 更新者
     */
    @field:TableField(fill = FieldFill.INSERT_UPDATE)
    override var updateBy: Long? = null

    /**
     * 更新时间
     */
    @field:TableField(fill = FieldFill.INSERT_UPDATE)
    override var updateTime: LocalDateTime? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseMixinEntity) return false

        if (createBy != other.createBy) return false
        if (createTime != other.createTime) return false
        if (updateBy != other.updateBy) return false
        if (updateTime != other.updateTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = createBy?.hashCode() ?: 0
        result = 31 * result + (createTime?.hashCode() ?: 0)
        result = 31 * result + (updateBy?.hashCode() ?: 0)
        result = 31 * result + (updateTime?.hashCode() ?: 0)
        return result
    }


}