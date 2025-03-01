package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.modules.system.domain.entity.SysLoginLogEntity
import org.zipper.common.core.domain.mixin.sys.SysLoginLogMixin
import org.zipper.modules.system.excel.ExcelDictConvert
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 系统访问记录视图对象 sys_logininfor
 *
 * @author Michelle.Chung
 * @date 2023-02-07
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysLoginLogEntity::class)
class SysLoginLogVo : SysLoginLogMixin, Serializable {
    /**
     * 访问ID
     */
    @field:ExcelProperty(value = ["序号"])
    override var infoId: Long? = null

    /**
     * 用户账号
     */
    @field:ExcelProperty(value = ["用户账号"])
    override var userName: String? = null

    /**
     * 客户端
     */
    @field:ExcelProperty(value = ["客户端"])
    override var clientKey: String? = null

    /**
     * 设备类型
     */
    @field:ExcelProperty(value = ["设备类型"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "sys_device_type")
    override var deviceType: String? = null

    /**
     * 登录状态（0成功 1失败）
     */
    @field:ExcelProperty(value = ["登录状态"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "sys_common_status")
    override var status: String? = null

    /**
     * 登录IP地址
     */
    @field:ExcelProperty(value = ["登录地址"])
    override var ipaddr: String? = null

    /**
     * 登录地点
     */
    @field:ExcelProperty(value = ["登录地点"])
    override var loginLocation: String? = null

    /**
     * 浏览器类型
     */
    @field:ExcelProperty(value = ["浏览器"])
    override var browser: String? = null

    /**
     * 操作系统
     */
    @field:ExcelProperty(value = ["操作系统"])
    override var os: String? = null


    /**
     * 提示消息
     */
    @field:ExcelProperty(value = ["提示消息"])
    override var msg: String? = null

    /**
     * 访问时间
     */
    @field:ExcelProperty(value = ["访问时间"])
    override var loginTime: LocalDateTime? = null

}
