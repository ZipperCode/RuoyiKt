package org.zipper.modules.account.domain.param

import jakarta.validation.constraints.NotNull

class DispatchUserParam {
    val ids: List<Long> = emptyList()

    @field:NotNull(message = "分配用户不能为空")
    val userId: Long? = null
}