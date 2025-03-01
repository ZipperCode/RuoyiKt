package org.zipper.modules.system.domain.bo

import io.github.linpeilie.annotations.AutoMapper
import io.github.linpeilie.annotations.AutoMappers
import org.zipper.framework.log.event.OperLogEvent
import org.zipper.modules.system.domain.entity.SysOperateLogEntity
import org.zipper.common.core.domain.mixin.sys.SysOperateLogMixin
import java.util.*

/**
 * 操作日志记录业务对象 sys_oper_log
 *
 * @author Michelle.Chung
 * @date 2023-02-07
 */
@AutoMappers(
    AutoMapper(target = SysOperateLogEntity::class, reverseConvertGenerate = false),
    AutoMapper(target = OperLogEvent::class)
)
class SysOperateLogBo : SysOperateLogMixin {
    /**
     * 日志主键
     */
    override var operId: Long? = null

    /**
     * 模块标题
     */
    override var title: String? = null

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    override var businessType: Int? = null

    /**
     * 业务类型数组
     */
    var businessTypes: Array<Int> = emptyArray()

    /**
     * 方法名称
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
     * 请求URL
     */
    override var operUrl: String? = null

    /**
     * 主机地址
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

    /**
     * 请求参数
     */
    val params: Map<String, Any> = HashMap()
}
