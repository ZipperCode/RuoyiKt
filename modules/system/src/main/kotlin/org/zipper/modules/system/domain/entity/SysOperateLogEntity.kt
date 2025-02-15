package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.modules.system.domain.mixin.SysOperateLogMixin
import java.io.Serializable
import java.util.*

/**
 * 操作日志记录表 oper_log
 * opera
 *
 * @author Lion Li
 */
@TableName("sys_oper_log")
open class SysOperateLogEntity : SysOperateLogMixin, Serializable {
    /**
     * 日志主键
     */
    @field:TableId(value = "oper_id")
    override var operId: Long? = null

    /**
     * 操作模块
     */
    override var title: String? = null

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    override var businessType: Int? = null

    /**
     * 请求方法
     */
    override var method: String? = null

    /**
     * 请求方式
     */
    override var requestMethod: String? = null

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    override var operatorType: Int? = null

    /**
     * 操作人员
     */
    override var operName: String? = null

    /**
     * 部门名称
     */
    override var deptName: String? = null

    /**
     * 请求url
     */
    override var operUrl: String? = null

    /**
     * 操作地址
     */
    override var operIp: String? = null

    /**
     * 操作地点
     */
    override var operLocation: String? = null

    /**
     * 请求参数
     */
    override var operParam: String? = null

    /**
     * 返回参数
     */
    override var jsonResult: String? = null

    /**
     * 操作状态（0正常 1异常）
     */
    override var status: Int = 0

    /**
     * 错误消息
     */
    override var errorMsg: String? = null

    /**
     * 操作时间
     */
    override var operTime: Date? = null

    /**
     * 消耗时间
     */
    override var costTime: Long? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SysOperateLogEntity) return false

        if (operId != other.operId) return false
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
