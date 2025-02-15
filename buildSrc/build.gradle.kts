plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.plugin)
    implementation(libs.kotlin.spring.plugin)
}

gradlePlugin {
    plugins {
        register("module") {
            id = "module"
            implementationClass = "org.zipper.build.plugin.ModulePlugin"
        }
    }
}