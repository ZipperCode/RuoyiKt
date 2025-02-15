plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(libs.ip2region)
}
kotlin {
    jvmToolchain(17)
}