plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.commons.excel)
    implementation(projects.commons.oss)
    implementation(projects.commons.sensitive)
    implementation(projects.framework.bootStarterWeb)
    implementation(projects.framework.bootStarterMybatis)
    implementation(projects.framework.bootStarterSecurity)
    implementation(projects.framework.bootStarterRedis)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterLog)
    implementation(projects.optionals.bootStarterTranslation)
    implementation(projects.optionals.bootStarterEncrypt)
    implementation(projects.optionals.bootStarterRatelimiter)
    implementation(projects.optionals.bootStarterIp2area)
    implementation(projects.optionals.bootStarterIdempotent)

    kapt(libs.mapstruct.plus.processor)
    implementation(libs.springboot.validation)
}
