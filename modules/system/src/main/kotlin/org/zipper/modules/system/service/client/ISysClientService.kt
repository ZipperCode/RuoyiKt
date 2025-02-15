package org.zipper.modules.system.service.client

import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.system.domain.entity.SysClientEntity
import org.zipper.modules.system.domain.param.SysClientQueryParam
import org.zipper.modules.system.domain.param.SysClientSaveParam
import org.zipper.modules.system.domain.vo.SysClientVo


/**
 * 客户端管理Service接口
 *
 * @author Michelle.Chung
 * @date 2023-06-18
 */
interface ISysClientService {
    /**
     * 查询客户端管理
     */
    fun queryById(id: Long?): SysClientVo?

    /**
     * 查询客户端信息基于客户端id
     */
    fun queryByClientId(clientId: String?): SysClientEntity?

    /**
     * 查询客户端管理列表
     */
    fun queryPageList(param: SysClientQueryParam, pageQuery: PageQuery): TableDataInfo<SysClientVo>

    /**
     * 查询客户端管理列表
     */
    fun queryList(): List<SysClientVo>

    /**
     * 新增客户端管理
     */
    fun insert(param: SysClientSaveParam): Boolean

    /**
     * 修改客户端管理
     */
    fun update(param: SysClientSaveParam): Boolean

    /**
     * 修改状态
     */
    fun updateUserStatus(id: Long, status: String): Int

    /**
     * 校验并批量删除客户端管理信息
     */
    fun deleteWithValidByIds(ids: Collection<Long>, isValid: Boolean): Boolean
}
