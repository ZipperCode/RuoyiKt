package org.zipper.modules.system.domain.mixin

interface SysNoticeMixin {
    /**
     * 公告ID
     */
    var noticeId: Long?

    /**
     * 公告标题
     */
    var noticeTitle: String?

    /**
     * 公告类型（1通知 2公告）
     */
    var noticeType: String?

    /**
     * 公告内容
     */
    var noticeContent: String?

    /**
     * 公告状态（0正常 1关闭）
     */
    var status: String?

    /**
     * 备注
     */
    var remark: String?
}