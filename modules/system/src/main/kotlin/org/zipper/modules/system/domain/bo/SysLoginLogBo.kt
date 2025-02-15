package org.zipper.modules.system.domain.bo

import io.github.linpeilie.annotations.AutoMapper
import org.zipper.modules.system.domain.entity.SysLoginLogEntity
import org.zipper.modules.system.domain.mixin.SysLoginLogMixin
import java.time.LocalDateTime
import java.util.*

/**
 * 系统访问记录业务对象 sys_logininfor
 *
 * @author Michelle.Chung
 */
@AutoMapper(target = SysLoginLogEntity::class, reverseConvertGenerate = false)
class SysLoginLogBo : SysLoginLogMixin {
    /**
     * 访问ID
     */
    override var infoId: Long? = null

    /**
     * 用户账号
     */
    override var userName: String? = null

    /**
     * 客户端
     */
    override var clientKey: String? = null

    /**
     * 设备类型
     */
    override var deviceType: String? = null

    /**
     * 登录IP地址
     */
    override var ipaddr: String? = null

    /**
     * 登录地点
     */
    override var loginLocation: String? = null

    /**
     * 浏览器类型
     */
    override var browser: String? = null

    /**
     * 操作系统
     */
    override var os: String? = null

    /**
     * 登录状态（0成功 1失败）
     */
    override var status: String? = null

    /**
     * 提示消息
     */
    override var msg: String? = null

    /**
     * 访问时间
     */
    override var loginTime: LocalDateTime? = null

    /**
     * 请求参数
     */
    var params: Map<String, Any> = HashMap()
}
