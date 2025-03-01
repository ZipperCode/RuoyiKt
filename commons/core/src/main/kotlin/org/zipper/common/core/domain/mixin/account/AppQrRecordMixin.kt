package org.zipper.common.core.domain.mixin.account

interface AppQrRecordMixin {

    var id:Long?

    var accountId: Long?

    var bindUserId: Long?

    var used: Int?
}