package org.zipper.modules.account.service

import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.account.domain.param.AppAccountParam
import org.zipper.modules.account.domain.param.AppAccountRecordParam
import org.zipper.modules.account.domain.param.UploadAccountParam
import org.zipper.modules.account.domain.vo.*

interface AppAccountService {
    /**
     * 新增
     */
    fun add(param: AppAccountParam): Boolean

    /**
     * 编辑
     */
    fun edit(param: AppAccountParam): Boolean

    /**
     * 删除数据
     */
    fun delete(ids: Array<Long>)

    /**
     * 修改状态
     */
    fun updateStatus(ids: List<Long>, status: Int): Int

    /**
     * 解绑数据
     */
    fun unBind(id: Long): Boolean

    /**
     * 批量上传
     */
    fun batchUpload(param: UploadAccountParam): AccountUploadResultVo

    /**
     * 获取信息
     */
    fun getInfo(id: Long): AppAccountVo?


    fun exportList(param: AppAccountParam): List<AccountExportVo>
    /**
     * 页面列表
     */
    fun pageList(param: AppAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo>

    /**
     * 记录列表
     */
    fun recordPageList(param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppAccountRecordVo>

    /**
     * 分配数据
     */
    fun dispatch(param: AppAccountParam): AppDispatchVo
}