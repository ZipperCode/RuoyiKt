plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    api(libs.alibaba.excel)
    implementation(libs.jakarta.validation)
}
