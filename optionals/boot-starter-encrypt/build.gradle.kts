plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(libs.springboot.web) {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation(libs.mybatis.plus.boot)
}
