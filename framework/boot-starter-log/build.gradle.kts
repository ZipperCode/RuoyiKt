plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterSecurity)

    implementation(libs.alibaba.thread.local)
}
