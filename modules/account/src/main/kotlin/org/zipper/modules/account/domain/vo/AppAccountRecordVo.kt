package org.zipper.modules.account.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.common.core.domain.mixin.account.AppAccountRecordMixin
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.account.domain.entity.AppAccountRecordEntity

@ExcelIgnoreUnannotated
@AutoMapper(target = AppAccountRecordEntity::class, reverseConvertGenerate = true)
class AppAccountRecordVo : BaseMixinVo(), AppAccountRecordMixin {
    override var id: Long? = null
    override var accountId: Long? = null
    override var bindUserId: Long? = null
    override var used: Int? = null
    override var classify: Int? = null
    /**
     * 绑定的用户名
     */
    var bindUser: String? = null

    /**
     * 分配用户
     */
    var createUser: String? = null

    /**
     * 查询记录是使用
     * 账号数据
     */
    var account: String? = null

    /**
     * 查询每个用户的数据
     */
    var count: Int = 0

}