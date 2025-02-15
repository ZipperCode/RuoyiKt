package org.zipper.modules.system.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zipper.framework.core.xss.Xss
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysNoticeEntity
import org.zipper.modules.system.domain.mixin.SysNoticeMixin

/**
 * 通知公告业务对象 dto
 *
 */
@AutoMapper(target = SysNoticeEntity::class, reverseConvertGenerate = false)
class SysNoticeParam : BaseMixinVo(), SysNoticeMixin {
    /**
     * 公告ID
     */
    override var noticeId: Long? = null

    /**
     * 公告标题
     */
    @field:Xss(message = "公告标题不能包含脚本字符")
    @field:NotBlank(message = "公告标题不能为空")
    @field:Size(
        min = 0,
        max = 50,
        message = "公告标题不能超过{max}个字符"
    )
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
    var createByName: String? = null
}
