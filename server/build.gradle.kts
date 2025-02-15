import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("module")
}

val active = System.getenv("profile") ?: "dev"

val replaceProperties = mutableMapOf<String, Any>(
    "profileActive" to active,
    "appName" to "RuoyiKt",
    "version" to version,
    "logging.level" to if (active == "dev") "debug" else "info"
)

dependencies {
    implementation(projects.commons.core)
    // compileOnly(projects.framework.bootStarterRedis)
    implementation(projects.modules.system)
    implementation(projects.modules.storage)
    implementation(libs.mybatis.plus.boot)
    kapt(libs.springboot.configuration.processors)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    archiveFileName.set("app.jar")
}

tasks.processResources {
    filesMatching("application.yml") {
        filter<ReplaceTokens>(mapOf("tokens" to replaceProperties))
    }
}