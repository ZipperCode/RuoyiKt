package org.zipper.framework.security.config

import cn.dev33.satoken.dao.SaTokenDao
import cn.dev33.satoken.jwt.StpLogicJwtForSimple
import cn.dev33.satoken.stp.StpInterface
import cn.dev33.satoken.stp.StpLogic
import org.zipper.framework.security.satoken.dao.PlusSaTokenDao
import org.zipper.framework.security.satoken.service.SaPermissionImpl
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.zipper.common.core.factory.YmlPropertySourceFactory

/**
 * sa-token 配置
 *
 * @author Lion Li
 */
@AutoConfiguration
class SaTokenConfig {
    /**
     * Sa-Token 整合 jwt (简单模式)
     */
    @Bean
    fun getStpLogicJwt(): StpLogic {
        return StpLogicJwtForSimple()
    }

    /**
     * 权限接口实现(使用bean注入方便用户替换)
     */
    @Bean
    fun stpInterface(): StpInterface {
        return SaPermissionImpl()
    }

    /**
     * 自定义dao层存储
     */
    @Bean
    fun saTokenDao(): SaTokenDao {
        return PlusSaTokenDao()
    }
}
