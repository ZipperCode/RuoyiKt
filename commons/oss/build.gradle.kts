plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(libs.aws.java.sdk)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterRedis)
}
