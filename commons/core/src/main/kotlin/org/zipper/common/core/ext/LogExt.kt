package org.zipper.common.core.ext

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val <reified T : Any> T.log: Logger
    inline get() = LoggerFactory.getLogger(T::class.java.name)