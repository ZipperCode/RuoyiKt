package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity

/**
 * 通知公告表 sys_notice
 *
 */
@TableName("sys_notice")
class SysNoticeEntity : BaseMixinEntity() {
    /**
     * 公告ID
     */
    @field:TableId(value = "notice_id")
    var noticeId: Long? = null

    /**
     * 公告标题
     */
    var noticeTitle: String? = null

    /**
     * 公告类型（1通知 2公告）
     */
    var noticeType: String? = null

    /**
     * 公告内容
     */
    var noticeContent: String? = null

    /**
     * 公告状态（0正常 1关闭）
     */
    var status: String? = null

    /**
     * 备注
     */
    var remark: String? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SysNoticeEntity) return false
        if (!super.equals(other)) return false

        if (noticeId != other.noticeId) return false
        if (noticeTitle != other.noticeTitle) return false
        if (noticeType != other.noticeType) return false
        if (noticeContent != other.noticeContent) return false
        if (status != other.status) return false
        if (remark != other.remark) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (noticeId?.hashCode() ?: 0)
        result = 31 * result + (noticeTitle?.hashCode() ?: 0)
        result = 31 * result + (noticeType?.hashCode() ?: 0)
        result = 31 * result + (noticeContent?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (remark?.hashCode() ?: 0)
        return result
    }

}
