package org.zipper.modules.system.service.log

import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.system.domain.bo.SysOperateLogBo
import org.zipper.modules.system.domain.vo.SysOperateLogVo

/**
 * 操作日志 服务层
 *
 * @author Lion Li
 */
interface ISysOperLogService {
    fun selectPageOperLogList(operLog: SysOperateLogBo, pageQuery: PageQuery): TableDataInfo<SysOperateLogVo>

    /**
     * 新增操作日志
     *
     * @param bo 操作日志对象
     */
    fun insertOperlog(bo: SysOperateLogBo)

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    fun selectOperLogList(operLog: SysOperateLogBo): List<SysOperateLogVo>

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    fun deleteOperLogByIds(operIds: Array<Long>): Int

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    fun selectOperLogById(operId: Long?): SysOperateLogVo?

    /**
     * 清空操作日志
     */
    fun cleanOperLog()
}
