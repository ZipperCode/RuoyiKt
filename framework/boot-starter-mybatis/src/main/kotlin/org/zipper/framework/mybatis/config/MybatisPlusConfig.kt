package org.zipper.framework.mybatis.config

import cn.hutool.core.net.NetUtil
import com.baomidou.mybatisplus.autoconfigure.DdlApplicationRunner
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator
import com.baomidou.mybatisplus.extension.ddl.IDdl
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.zipper.common.core.factory.YmlPropertySourceFactory
import org.zipper.framework.mybatis.handler.InjectionMetaObjectHandler
import org.zipper.framework.mybatis.interceptor.PlusDataPermissionInterceptor

/**
 * mybatis-plus配置类(下方注释有插件介绍)
 *
 * @author Lion Li
 */
@EnableTransactionManagement(proxyTargetClass = true)
@AutoConfiguration(before = [MybatisPlusAutoConfiguration::class])
//@PropertySource(value = ["classpath:common-mybatis.yml"], factory = YmlPropertySourceFactory::class)
class MybatisPlusConfig {
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        // 数据权限处理
        interceptor.addInnerInterceptor(dataPermissionInterceptor())
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor())
        // 乐观锁插件
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor())
        return interceptor
    }

    /**
     * 数据权限拦截器
     */
    fun dataPermissionInterceptor(): PlusDataPermissionInterceptor {
        return PlusDataPermissionInterceptor()
    }

    /**
     * 分页插件，自动识别数据库类型
     */
    fun paginationInnerInterceptor(): PaginationInnerInterceptor {
        val paginationInnerInterceptor = PaginationInnerInterceptor()
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.maxLimit = -1L
        // 分页合理化
        paginationInnerInterceptor.isOverflow = true
        return paginationInnerInterceptor
    }

    /**
     * 乐观锁插件
     */
    fun optimisticLockerInnerInterceptor(): OptimisticLockerInnerInterceptor {
        return OptimisticLockerInnerInterceptor()
    }

    /**
     * 元对象字段填充控制器
     */
    @Bean
    fun metaObjectHandler(): MetaObjectHandler {
        return InjectionMetaObjectHandler()
    }

    /**
     * 使用网卡信息绑定雪花生成器
     * 防止集群雪花ID重复
     */
    @Bean
    fun idGenerator(): IdentifierGenerator {
        return DefaultIdentifierGenerator(NetUtil.getLocalhost())
    }

    /**
     * PaginationInnerInterceptor 分页插件，自动识别数据库类型
     * https://baomidou.com/pages/97710a/
     * OptimisticLockerInnerInterceptor 乐观锁插件
     * https://baomidou.com/pages/0d93c0/
     * MetaObjectHandler 元对象字段填充控制器
     * https://baomidou.com/pages/4c6bcf/
     * ISqlInjector sql注入器
     * https://baomidou.com/pages/42ea4a/
     * BlockAttackInnerInterceptor 如果是对全表的删除或更新操作，就会终止该操作
     * https://baomidou.com/pages/f9a237/
     * IllegalSQLInnerInterceptor sql性能规范插件(垃圾SQL拦截)
     * IdentifierGenerator 自定义主键策略
     * https://baomidou.com/pages/568eb2/
     * TenantLineInnerInterceptor 多租户插件
     * https://baomidou.com/pages/aef2f2/
     * DynamicTableNameInnerInterceptor 动态表名插件
     * https://baomidou.com/pages/2a45ff/
     */
    @Bean
    fun ddlApplicationRunner(@Autowired(required = false) ddlList: List<IDdl?>?): DdlApplicationRunner {
        return DdlApplicationRunner(ddlList)
    }
}
