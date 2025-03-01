package org.zipper.server

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication(scanBasePackages = ["org.zipper"])
@MapperScan("org.zipper.modules.**.mapper")
open class RuoyiApplication


fun main(args: Array<String>) {
    SpringApplication.run(RuoyiApplication::class.java, *args)
    println("(♥◠‿◠)ﾉﾞ  RuoYi-Vue-Plus启动成功   ლ(´ڡ`ლ)ﾞ")
}