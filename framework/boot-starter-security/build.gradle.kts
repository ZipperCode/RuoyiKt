
plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarterRedis)
    api(libs.satoken.spring.boot3){
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation(libs.satoken.core)
    implementation(libs.satoken.jwt)
    implementation(libs.jakarta.validation)
}
