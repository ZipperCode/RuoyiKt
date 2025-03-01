package org.zipper.modules.system.domain.vo

import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysNoticeEntity
import org.zipper.common.core.domain.mixin.sys.SysNoticeMixin
import org.zipper.optional.translation.annotation.Translation
import org.zipper.optional.translation.constant.TransConstant
import java.io.Serializable

/**
 * 通知公告视图对象 sys_notice
 *
 * @author Michelle.Chung
 */
@AutoMapper(target = SysNoticeEntity::class)
class SysNoticeVo : BaseMixinVo(), SysNoticeMixin, Serializable {
    /**
     * 公告ID
     */
    override var noticeId: Long? = null

    /**
     * 公告标题
     */
    override var noticeTitle: String? = null

    /**
     * 公告类型（1通知 2公告）
     */
    override var noticeType: String? = null

    /**
     * 公告内容
     */
    override var noticeContent: String? = null

    /**
     * 公告状态（0正常 1关闭）
     */
    override var status: String? = null

    /**
     * 备注
     */
    override var remark: String? = null

    /**
     * 创建人名称
     */
    @field:Translation(type = TransConstant.USER_ID_TO_NAME, mapper = "createBy")
    var createByName: String? = null
}
