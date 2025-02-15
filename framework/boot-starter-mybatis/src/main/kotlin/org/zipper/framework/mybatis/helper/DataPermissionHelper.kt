package org.zipper.framework.mybatis.helper

import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper
import org.zipper.common.core.ext.anyForceCast
import org.zipper.common.core.ext.forceCast
import org.zipper.framework.security.utils.SaTokenHelper
import java.util.function.Supplier

/**
 * 数据权限助手
 *
 * @author Lion Li
 * @version 3.5.0
 */
object DataPermissionHelper {
    private const val DATA_PERMISSION_KEY = "data:permission"

    fun <T> getVariable(key: String): T? {
        return getContext()[key]?.anyForceCast()
    }

    fun setVariable(key: String, value: Any?) {
        getContext()[key] = value
    }

    fun getContext(): MutableMap<Any, Any?> {
        var attribute = SaTokenHelper[DATA_PERMISSION_KEY]
        if (attribute == null) {
            SaTokenHelper[DATA_PERMISSION_KEY] = HashMap<Any, Any>()
            attribute = SaTokenHelper[DATA_PERMISSION_KEY]
        }
        if (attribute is MutableMap<*, *>) {
            return attribute.forceCast()
        }
        throw NullPointerException("data permission context type exception")
    }

    /**
     * 开启忽略数据权限(开启后需手动调用 [.disableIgnore] 关闭)
     */
    fun enableIgnore() {
        InterceptorIgnoreHelper.handle(IgnoreStrategy.builder().dataPermission(true).build())
    }

    /**
     * 关闭忽略数据权限
     */
    fun disableIgnore() {
        InterceptorIgnoreHelper.clearIgnoreStrategy()
    }

    /**
     * 在忽略数据权限中执行
     *
     * @param handle 处理执行方法
     */
    fun ignore(handle: Runnable) {
        enableIgnore()
        try {
            handle.run()
        } finally {
            disableIgnore()
        }
    }

    /**
     * 在忽略数据权限中执行
     *
     * @param handle 处理执行方法
     */
    fun <T> ignore(handle: Supplier<T>): T {
        enableIgnore()
        try {
            return handle.get()
        } finally {
            disableIgnore()
        }
    }
}
