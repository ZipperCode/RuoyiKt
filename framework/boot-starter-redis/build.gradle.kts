plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(libs.jackson)
    implementation(libs.redisson.spring)
}
