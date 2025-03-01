package org.zipper.common.core.domain.mixin.account

interface AppAccountRecordMixin {

    var id:Long?

    var accountId: Long?

    var bindUserId: Long?

    var classify: Int?

    var used: Int?
}