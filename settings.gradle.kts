pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}


plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "RuoyiKt"
// 模块访问
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("framework")
//include("commons")
// 核心通用逻辑
include("commons:core")
// 阿里Excel
include("commons:excel")
// oss
include("commons:oss")
include("commons:sensitive")
// 三方库starter引入启动
include("framework:boot-starter")
//include("framework:boot-starter-json")
include("framework:boot-starter-log")
include("framework:boot-starter-redis")
include("framework:boot-starter-security")
include("framework:boot-starter-mybatis")
include("framework:boot-starter-web")
//include("optionals")

include("optionals:boot-starter-ip2area")
include("optionals:boot-starter-idempotent")
include("optionals:boot-starter-translation")
include("optionals:boot-starter-websocket")
include("optionals:boot-starter-encrypt")
include("optionals:boot-starter-ratelimiter")
include("optionals:boot-starter-job")
//include("modules")

include("modules:system")
include("modules:storage")
include("server")
