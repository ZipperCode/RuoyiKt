package org.zipper.common.core.domain.mixin.sys

import java.util.*

interface SysOperateLogMixin {
    /**
     * 日志主键
     */
    var operId: Long?

    /**
     * 操作模块
     */
    var title: String?

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    var businessType: Int?

    /**
     * 请求方法
     */
    var method: String?

    /**
     * 请求方式
     */
    var requestMethod: String?

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    var operatorType: Int?

    /**
     * 操作人员
     */
    var operName: String?

    /**
     * 部门名称
     */
    var deptName: String?

    /**
     * 请求url
     */
    var operUrl: String?

    /**
     * 操作地址
     */
    var operIp: String?

    /**
     * 操作地点
     */
    var operLocation: String?

    /**
     * 请求参数
     */
    var operParam: String?

    /**
     * 返回参数
     */
    var jsonResult: String?

    /**
     * 操作状态（0正常 1异常）
     */
    var status: Int

    /**
     * 错误消息
     */
    var errorMsg: String?

    /**
     * 操作时间
     */
    var operTime: Date?

    /**
     * 消耗时间
     */
    var costTime: Long?
}