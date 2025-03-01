package org.zipper.modules.account.domain.param

import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.common.core.domain.mixin.account.AppAccountRecordMixin
import org.zipper.framework.mybatis.core.domain.BaseMixinParam


class AppAccountRecordParam : BaseMixinParam(), AppAccountRecordMixin {
    override var id: Long? = null
    override var accountId: Long? = null
    override var bindUserId: Long? = null
    override var classify: Int? = null
    override var used: Int? = null

    var account: String?= null

    var bindUser: String? = null

    var createUser: String? = null
}