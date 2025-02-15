package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.event.WebSocketMsgEvent
import org.zipper.common.core.ext.currentSpringContext
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.bo.SysNoticeBo
import org.zipper.modules.system.domain.vo.SysNoticeVo
import org.zipper.modules.system.service.dict.DictService
import org.zipper.modules.system.service.notice.ISysNoticeService

/**
 * 公告 信息操作处理
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/notice")
class SysNoticeController(
    private val noticeService: ISysNoticeService,
    private val dictService: DictService
) {
    /**
     * 获取通知公告列表
     */
    @SaCheckPermission("system:notice:list")
    @GetMapping("/list")
    fun list(notice: SysNoticeBo?, pageQuery: PageQuery?): TableDataInfo<SysNoticeVo> {
        return noticeService.selectPageNoticeList(notice!!, pageQuery!!)
    }

    /**
     * 根据通知公告编号获取详细信息
     *
     * @param noticeId 公告ID
     */
    @SaCheckPermission("system:notice:query")
    @GetMapping(value = ["/{noticeId}"])
    @ResultBody
    fun getInfo(@PathVariable noticeId: Long): SysNoticeVo? {
        return noticeService.selectNoticeById(noticeId)
    }

    /**
     * 新增通知公告
     */
    @SaCheckPermission("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody notice: SysNoticeBo) {
        noticeService.insertNotice(notice).validateRow()
        val type = dictService.getDictLabel("sys_notice_type", notice.noticeType!!)
        currentSpringContext().publishEvent(WebSocketMsgEvent("[$type]${notice.noticeTitle}"))
    }

    /**
     * 修改通知公告
     */
    @SaCheckPermission("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody notice: SysNoticeBo) {
        noticeService.updateNotice(notice).validateRow()
    }

    /**
     * 删除通知公告
     *
     * @param noticeIds 公告ID串
     */
    @SaCheckPermission("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    @ResultBody
    fun remove(@PathVariable noticeIds: Array<Long>) {
        noticeService.deleteNoticeByIds(noticeIds).validateRow()
    }
}
