package org.zipper.framework.mybatis.handler

import cn.hutool.http.HttpStatus
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.zipper.common.core.domain.model.LoginUser
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.log
import org.zipper.common.core.ext.withType
import org.zipper.framework.mybatis.core.domain.CreatorMixin
import org.zipper.framework.mybatis.core.domain.UpdaterMixin
import org.zipper.framework.security.utils.LoginHelper
import java.time.LocalDateTime

/**
 * MP注入处理器
 *
 * @author Lion Li
 * @date 2021/4/25
 */
class InjectionMetaObjectHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        try {
            metaObject.originalObject.withType<CreatorMixin> {
                val current = createTime ?: LocalDateTime.now()
                createTime = current
                val loginUser = getLoginUser() ?: return
                createBy = createBy ?: loginUser.userId
            }
            metaObject.originalObject.withType<UpdaterMixin> {
                updateTime = updateTime ?: LocalDateTime.now()
                updateBy = updateBy ?: getLoginUser()?.userId
            }

        } catch (e: Exception) {
            throw ServiceException("自动注入异常 => " + e.message, HttpStatus.HTTP_UNAUTHORIZED)
        }
    }

    override fun updateFill(metaObject: MetaObject) {
        try {
            metaObject.originalObject.withType<UpdaterMixin> {
                updateTime = updateTime ?: LocalDateTime.now()
                updateBy = updateBy ?: getLoginUser()?.userId
            }

        } catch (e: Exception) {
            throw ServiceException("自动注入异常 => " + e.message, HttpStatus.HTTP_UNAUTHORIZED)
        }
    }

    /**
     * 获取登录用户名
     */
    private fun getLoginUser(): LoginUser? {
        try {
            return LoginHelper.getLoginUser()
        } catch (e: java.lang.Exception) {
            log.warn("自动注入警告 => 用户未登录")
            return null
        }
    }
}
