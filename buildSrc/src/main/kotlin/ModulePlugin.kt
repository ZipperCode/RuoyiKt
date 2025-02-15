package org.zipper.build.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.repositories
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension

class ModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.jvm")
            apply(plugin = "org.jetbrains.kotlin.kapt")
            apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
            apply(plugin = "org.jetbrains.kotlin.plugin.spring")

            apply(plugin = "org.springframework.boot")
            apply(plugin = "io.spring.dependency-management")

            group = BuildConfig.GROUP
            version = BuildConfig.VERSION

            repositories {
                mavenCentral()
            }

            with(extensions.getByType(KotlinTopLevelExtension::class.java)) {
                jvmToolchain(17)
            }

            with(extensions.getByType(AllOpenExtension::class.java)) {
                annotations(
                    BuildConfig.ALL_OPEN,
                    "org.springframework.boot.autoconfigure.SpringBootApplication"
                )
            }

        }
    }
}