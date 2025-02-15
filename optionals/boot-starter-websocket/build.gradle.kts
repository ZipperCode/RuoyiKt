plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarter)
    implementation(projects.framework.bootStarterRedis)
    implementation(projects.framework.bootStarterSecurity)
    implementation(libs.springboot.websocket){
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
}
