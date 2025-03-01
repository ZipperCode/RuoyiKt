package org.zipper.modules.account.service

import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.account.domain.param.AppLinksParam
import org.zipper.modules.account.domain.vo.AppLinksVo

interface AppLinksService {

    fun add(param: AppLinksParam): Boolean
    fun edit(param: AppLinksParam): Boolean
    fun delete(ids: Array<Long>): Boolean

    fun checkExists(id: Long): Boolean

    fun getInfo(id: Long): AppLinksVo?

    fun pageList(param: AppLinksParam, pageQuery: PageQuery): TableDataInfo<AppLinksVo>
}