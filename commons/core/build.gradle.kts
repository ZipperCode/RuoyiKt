plugins {
    id("module")
}

dependencies {
    api(libs.hutool.all)
    api(libs.hutool.extra)
    api(libs.jakarta.servlet)
    api(libs.jakarta.validation)
    api(libs.springboot.parent)
    api(libs.springboot.aop)
    api(libs.apache.commons.lang3)

    api(libs.mapstruct.plus)

    compileOnly(libs.springboot.web) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }

}
