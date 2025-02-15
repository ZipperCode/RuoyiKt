package org.zipper.modules.storage.service

import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.storage.domain.bo.FileCreateBo
import org.zipper.modules.storage.domain.entity.SysFileRecordEntity
import org.zipper.modules.storage.domain.param.FileRecordPageParam
import org.zipper.modules.storage.domain.vo.FileRecordVo

interface FileRecordService {
    /**
     * 创建文件记录
     */
    fun create(fileCreateBo: FileCreateBo): FileRecordVo
    /**
     * 通过id查询
     */
    fun queryByIds(recordIds: Array<Long>): List<FileRecordVo>

    /**
     * 删除文件记录
     * 调用前确保文件删除
     */
    fun delete(recordIds: Array<Long>): List<SysFileRecordEntity>

    /**
     * 记录分页列表
     */
    fun pageList(param: FileRecordPageParam): TableDataInfo<FileRecordVo>
}