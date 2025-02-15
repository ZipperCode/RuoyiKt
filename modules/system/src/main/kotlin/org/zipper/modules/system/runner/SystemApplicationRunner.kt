package org.zipper.modules.system.runner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.zipper.common.core.ext.log
import org.zipper.common.core.framework.store.IStorageService
import org.zipper.modules.system.service.dept.DeptService
import org.zipper.modules.system.service.dict.DictService
import org.zipper.modules.system.service.oss.OssService
import org.zipper.modules.system.service.user.UserService
import org.zipper.modules.system.translation.*

/**
 * 初始化 system 模块对应业务数据
 *
 * @author Lion Li
 */
@Component
class SystemApplicationRunner : ApplicationRunner {

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        log.info("初始System成功")
    }

    @Lazy
    @Bean
    fun getDeptNameTranslation(deptService: DeptService) = DeptNameTranslationImpl(deptService)

    @Lazy
    @Bean
    fun getNicknameTranslation(userService: UserService) = NicknameTranslationImpl(userService)

    @Lazy
    @Bean
    fun getUserNameTranslation(userService: UserService) = UserNameTranslationImpl(userService)

    @Lazy
    @Bean
    fun getDictTypeTranslation(dictService: DictService) = DictTypeTranslationImpl(dictService)

    @Lazy
    @Bean
    fun getOssUrlTranslation(storageService: IStorageService) = OssUrlTranslationImpl(storageService)
}
