package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.modules.system.domain.mixin.SysLoginLogMixin
import java.io.Serializable
import java.time.LocalDateTime

/**
 * 系统访问记录表 sys_login_log
 */
@TableName("sys_login_log")
class SysLoginLogEntity : SysLoginLogMixin, Serializable {
    /**
     * ID
     */
    @field:TableId(value = "info_id")
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
     * 登录状态 0成功 1失败
     */
    override var status: String? = null

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
     * 提示消息
     */
    override var msg: String? = null

    /**
     * 访问时间
     */
    override var loginTime: LocalDateTime? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SysLoginLogEntity) return false

        if (infoId != other.infoId) return false
        if (userName != other.userName) return false
        if (clientKey != other.clientKey) return false
        if (deviceType != other.deviceType) return false
        if (status != other.status) return false
        if (ipaddr != other.ipaddr) return false
        if (loginLocation != other.loginLocation) return false
        if (browser != other.browser) return false
        if (os != other.os) return false
        if (msg != other.msg) return false
        if (loginTime != other.loginTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = infoId?.hashCode() ?: 0
        result = 31 * result + (userName?.hashCode() ?: 0)
        result = 31 * result + (clientKey?.hashCode() ?: 0)
        result = 31 * result + (deviceType?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (ipaddr?.hashCode() ?: 0)
        result = 31 * result + (loginLocation?.hashCode() ?: 0)
        result = 31 * result + (browser?.hashCode() ?: 0)
        result = 31 * result + (os?.hashCode() ?: 0)
        result = 31 * result + (msg?.hashCode() ?: 0)
        result = 31 * result + (loginTime?.hashCode() ?: 0)
        return result
    }


}
