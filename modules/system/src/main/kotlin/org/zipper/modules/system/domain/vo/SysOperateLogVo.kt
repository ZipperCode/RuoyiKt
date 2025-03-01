package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.modules.system.domain.entity.SysOperateLogEntity
import org.zipper.common.core.domain.mixin.sys.SysOperateLogMixin
import org.zipper.modules.system.excel.ExcelDictConvert
import java.io.Serializable
import java.util.*

/**
 * 操作日志记录视图对象 sys_oper_log
 *
 * @author Michelle.Chung
 * @date 2023-02-07
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysOperateLogEntity::class)
class SysOperateLogVo : SysOperateLogMixin, Serializable {
    /**
     * 日志主键
     */
    @field:ExcelProperty(value = ["日志主键"])
    override var operId: Long? = null

    /**
     * 模块标题
     */
    @field:ExcelProperty(value = ["操作模块"])
    override var title: String? = null

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @field:ExcelProperty(value = ["业务类型"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "sys_oper_type")
    override var businessType: Int? = null

    /**
     * 业务类型数组
     */
    var businessTypes: Array<Int> = emptyArray()

    /**
     * 方法名称
     */
    @field:ExcelProperty(value = ["请求方法"])
    override var method: String? = null

    /**
     * 请求方式
     */
    @field:ExcelProperty(value = ["请求方式"])
    override var requestMethod: String? = null

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @field:ExcelProperty(value = ["操作类别"], converter = ExcelDictConvert::class)
    @ExcelDictFormat(readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    override var operatorType: Int? = null

    /**
     * 操作人员
     */
    @field:ExcelProperty(value = ["操作人员"])
    override var operName: String? = null

    /**
     * 部门名称
     */
    @field:ExcelProperty(value = ["部门名称"])
    override var deptName: String? = null

    /**
     * 请求URL
     */
    @field:ExcelProperty(value = ["请求地址"])
    override var operUrl: String? = null

    /**
     * 主机地址
     */
    @field:ExcelProperty(value = ["操作地址"])
    override var operIp: String? = null

    /**
     * 操作地点
     */
    @field:ExcelProperty(value = ["操作地点"])
    override var operLocation: String? = null

    /**
     * 请求参数
     */
    @field:ExcelProperty(value = ["请求参数"])
    override var operParam: String? = null

    /**
     * 返回参数
     */
    @field:ExcelProperty(value = ["返回参数"])
    override var jsonResult: String? = null

    /**
     * 操作状态（0正常 1异常）
     */
    @field:ExcelProperty(value = ["状态"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "sys_common_status")
    override var status: Int = 0

    /**
     * 错误消息
     */
    @field:ExcelProperty(value = ["错误消息"])
    override var errorMsg: String? = null

    /**
     * 操作时间
     */
    @field:ExcelProperty(value = ["操作时间"])
    override var operTime: Date? = null

    /**
     * 消耗时间
     */
    @field:ExcelProperty(value = ["消耗时间"])
    override var costTime: Long? = null

}
