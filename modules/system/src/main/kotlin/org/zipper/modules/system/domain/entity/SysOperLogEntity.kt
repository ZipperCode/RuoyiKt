package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serial
import java.io.Serializable
import java.util.*

/**
 * 操作日志记录表 oper_log
 * opera
 *
 * @author Lion Li
 */
@TableName("sys_oper_log")
open class SysOperLogEntity : Serializable {
    /**
     * 日志主键
     */
    @field:TableId(value = "oper_id")
    var operId: Long? = null

    /**
     * 租户编号
     */
    var tenantId: String? = null

    /**
     * 操作模块
     */
    var title: String? = null

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    var businessType: Int? = null

    /**
     * 请求方法
     */
    var method: String? = null

    /**
     * 请求方式
     */
    var requestMethod: String? = null

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    var operatorType: Int? = null

    /**
     * 操作人员
     */
    var operName: String? = null

    /**
     * 部门名称
     */
    var deptName: String? = null

    /**
     * 请求url
     */
    var operUrl: String? = null

    /**
     * 操作地址
     */
    var operIp: String? = null

    /**
     * 操作地点
     */
    var operLocation: String? = null

    /**
     * 请求参数
     */
    var operParam: String? = null

    /**
     * 返回参数
     */
    var jsonResult: String? = null

    /**
     * 操作状态（0正常 1异常）
     */
    var status: Int = 0

    /**
     * 错误消息
     */
    var errorMsg: String? = null

    /**
     * 操作时间
     */
    var operTime: Date? = null

    /**
     * 消耗时间
     */
    var costTime: Long? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SysOperLogEntity) return false

        if (operId != other.operId) return false
        if (tenantId != other.tenantId) return false
        if (title != other.title) return false
        if (businessType != other.businessType) return false
        if (method != other.method) return false
        if (requestMethod != other.requestMethod) return false
        if (operatorType != other.operatorType) return false
        if (operName != other.operName) return false
        if (deptName != other.deptName) return false
        if (operUrl != other.operUrl) return false
        if (operIp != other.operIp) return false
        if (operLocation != other.operLocation) return false
        if (operParam != other.operParam) return false
        if (jsonResult != other.jsonResult) return false
        if (status != other.status) return false
        if (errorMsg != other.errorMsg) return false
        if (operTime != other.operTime) return false
        if (costTime != other.costTime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = operId?.hashCode() ?: 0
        result = 31 * result + (tenantId?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (businessType ?: 0)
        result = 31 * result + (method?.hashCode() ?: 0)
        result = 31 * result + (requestMethod?.hashCode() ?: 0)
        result = 31 * result + (operatorType ?: 0)
        result = 31 * result + (operName?.hashCode() ?: 0)
        result = 31 * result + (deptName?.hashCode() ?: 0)
        result = 31 * result + (operUrl?.hashCode() ?: 0)
        result = 31 * result + (operIp?.hashCode() ?: 0)
        result = 31 * result + (operLocation?.hashCode() ?: 0)
        result = 31 * result + (operParam?.hashCode() ?: 0)
        result = 31 * result + (jsonResult?.hashCode() ?: 0)
        result = 31 * result + status
        result = 31 * result + (errorMsg?.hashCode() ?: 0)
        result = 31 * result + (operTime?.hashCode() ?: 0)
        result = 31 * result + (costTime?.hashCode() ?: 0)
        return result
    }


}
