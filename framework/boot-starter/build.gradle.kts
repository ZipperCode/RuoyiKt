plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(libs.hibernate.validator)
    implementation(libs.jakarta.validation)
    api(libs.springboot.json)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}