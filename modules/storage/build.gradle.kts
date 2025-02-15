plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.commons.oss)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterMybatis)
    implementation(projects.framework.bootStarterWeb)
    implementation(projects.framework.bootStarterSecurity)
    implementation(projects.framework.bootStarterLog)
    implementation(projects.framework.bootStarterRedis)
    implementation(projects.optionals.bootStarterIdempotent)
    implementation(libs.springboot.validation)
    implementation(libs.springboot.doc)
    kapt(libs.mapstruct.plus.processor)
    implementation(kotlin("reflect"))
}