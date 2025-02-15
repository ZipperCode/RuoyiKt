package org.zipper.common.core.ext

@Suppress("UNCHECKED_CAST")
inline fun <reified V, reified T> Class<V>.forceCastClass(): Class<T> {
    return this as Class<T>
}

@Suppress("UNCHECKED_CAST")
inline fun <reified V : Any, reified T> V.forceCast(): T {
    return this as T
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.anyForceCast(): T {
    return this as T
}


@Suppress("UNCHECKED_CAST")
inline fun <reified V, reified T> V?.forceCastOrNull(): T? {
    return this as? T?
}

@Suppress("UNCHECKED_CAST")
fun <T> Any?.anyForceCastOrNull(): T? {
    return this as? T?
}

inline fun <reified T> Any?.withType(block: T.() -> Unit) {
    if (this is T) {
        block()
    }
}