plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterSecurity)
    api(libs.mybatis.plus.annotation)
    api(libs.mybatis.plus.generator)
    api(libs.mybatis.plus.boot)
    implementation(libs.dynamic.datasource)
    implementation(libs.mysql.driver)
}
