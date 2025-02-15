package org.zipper.modules.system.constant

import org.zipper.common.core.exception.MessageException

object UserMessageConst {

    val AuthGrantError: Exception get() = MessageException("auth.grant.type.error")

    val AuthBlockedError: Exception get() = MessageException("auth.grant.type.blocked")
}