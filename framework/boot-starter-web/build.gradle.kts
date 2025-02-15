plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarter)
    implementation(libs.jakarta.servlet)
    implementation(libs.springboot.web) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation(libs.springboot.undertow)
    implementation(libs.springboot.undertow)
    implementation(libs.jakarta.validation)
    implementation(libs.alibaba.thread.local)
}
