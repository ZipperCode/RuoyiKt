plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.commons.oss)
    implementation(projects.commons.excel)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterLog)
    implementation(projects.framework.bootStarterMybatis)
    implementation(projects.framework.bootStarterSecurity)
    implementation(projects.optionals.bootStarterIdempotent)
    kapt(libs.mapstruct.plus.processor)
}