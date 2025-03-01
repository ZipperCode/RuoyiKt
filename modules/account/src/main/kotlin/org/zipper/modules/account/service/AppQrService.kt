package org.zipper.modules.account.service

import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.account.domain.param.AppAccountRecordParam
import org.zipper.modules.account.domain.param.AppQrParam
import org.zipper.modules.account.domain.param.UploadAccountParam
import org.zipper.modules.account.domain.vo.AccountUploadResultVo
import org.zipper.modules.account.domain.vo.AppDispatchVo
import org.zipper.modules.account.domain.vo.AppQrRecordVo
import org.zipper.modules.account.domain.vo.AppQrVo

interface AppQrService {
    /**
     * 新增
     */
    fun add(param: AppQrParam): Boolean

    /**
     * 编辑
     */
    fun edit(param: AppQrParam): Boolean

    /**
     * 删除数据
     */
    fun delete(ids: Array<Long>)

    /**
     * 修改状态
     */
    fun updateStatus(id: Long, status: Int): Int

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
    fun getInfo(id: Long): AppQrVo?

    /**
     * 页面列表
     */
    fun pageList(param: AppQrParam, pageQuery: PageQuery): TableDataInfo<AppQrVo>

    /**
     * 记录列表
     */
    fun recordPageList(param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppQrRecordVo>

    /**
     * 分配数据
     */
    fun dispatch(param: AppQrParam): AppDispatchVo
}