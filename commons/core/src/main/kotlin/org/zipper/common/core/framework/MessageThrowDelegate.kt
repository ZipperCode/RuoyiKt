package org.zipper.common.core.framework

import org.zipper.common.core.exception.ServiceException
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class MessageThrowDelegate(
    private val messageCode: String,
    private vararg val args: Any?
) : ReadOnlyProperty<Any, Exception> {
    override fun getValue(thisRef: Any, property: KProperty<*>): Exception {
        return ServiceException(MessageUtils.message(messageCode, *args))
    }
}