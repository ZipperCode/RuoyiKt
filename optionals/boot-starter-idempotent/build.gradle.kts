plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarterRedis)
    implementation(libs.springboot.web) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation(libs.springboot.redis)
    implementation(libs.satoken.core)
}
