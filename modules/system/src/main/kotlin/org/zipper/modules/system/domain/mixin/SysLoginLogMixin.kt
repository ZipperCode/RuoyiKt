package org.zipper.modules.system.domain.mixin

import java.time.LocalDateTime
import java.util.*

interface SysLoginLogMixin {
    /**
     * ID
     */
    var infoId: Long?

    /**
     * 用户账号
     */
    var userName: String?

    /**
     * 客户端
     */
    var clientKey: String?

    /**
     * 设备类型
     */
    var deviceType: String?

    /**
     * 登录状态 0成功 1失败
     */
    var status: String?

    /**
     * 登录IP地址
     */
    var ipaddr: String?

    /**
     * 登录地点
     */
    var loginLocation: String?

    /**
     * 浏览器类型
     */
    var browser: String?

    /**
     * 操作系统
     */
    var os: String?

    /**
     * 提示消息
     */
    var msg: String?

    /**
     * 访问时间
     */
    var loginTime: LocalDateTime?

}