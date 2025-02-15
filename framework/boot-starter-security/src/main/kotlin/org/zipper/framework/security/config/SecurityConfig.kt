package org.zipper.framework.security.config

import cn.dev33.satoken.`fun`.SaFunction
import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.stp.StpUtil
import cn.hutool.extra.spring.SpringUtil
import jakarta.servlet.Filter
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.zipper.framework.security.config.properties.SecurityProperties
import org.zipper.framework.security.handler.AllUrlHandler

/**
 * 权限安全配置
 *
 * @author Lion Li
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties::class)
class SecurityConfig(
    private val securityProperties: SecurityProperties
) : WebMvcConfigurer {

    /**
     * 注册sa-token的拦截器
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        // 注册路由拦截器，自定义验证规则
        registry
            .addInterceptor(SaInterceptor {
                val allUrlHandler = SpringUtil.getBean(AllUrlHandler::class.java)
                // 登录验证 -- 排除多个路径
                SaRouter // 获取所有的
                    .match(allUrlHandler.urls) // 对未排除的路径进行检查
                    .check(SaFunction {
                        // 检查是否登录 是否有token
                        StpUtil.checkLogin()

                        // 检查 header 与 param 里的 clientid 与 token 里的是否一致
//                        val request = Thread.currentThread().getRequestAttributes()?.request
//                        val headerCid = request?.getDecodeHeader(LoginHelper.CLIENT_KEY)
//                        val paramCid = request?.getParameter(LoginHelper.CLIENT_KEY)
//                        val clientId = StpUtil.getExtra(LoginHelper.CLIENT_KEY)?.toString()
//                        if (!StringUtils.equalsAny(headerCid, paramCid)) {
//                            // token 无效
//                            throw NotLoginException.newInstance(
//                                StpUtil.getLoginType(),
//                                "-100", "客户端ID与Token不匹配",
//                                StpUtil.getTokenValue()
//                            )
//                        }
                    })
            })
            .addPathPatterns("/**") // 排除不需要拦截的路径
            .excludePathPatterns(*securityProperties.excludes)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        //添加映射路径
        registry.addMapping("/**") //是否发送Cookie
            .allowCredentials(true) //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
            .allowedOriginPatterns("*") //放行哪些请求方式
            .allowedMethods("GET", "POST", "PUT", "DELETE") //.allowedMethods("*") //或者放行全部
            //放行哪些原始请求头部信息
            .allowedHeaders("*") //暴露哪些原始请求头部信息
            .exposedHeaders("*")
    }
}
