
plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarterRedis)
    implementation(libs.redisson.spring)
}
