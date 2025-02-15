plugins {
    id("module")
}

dependencies {
    implementation(projects.commons.core)
    implementation(projects.framework.bootStarter)
//    implementation(project(":framework:boot-starter-core"))
//    implementation(Libs.Apache.Commons.Lang3)
//    implementation(Libs.SaToken.SpringBootStarter)
}
