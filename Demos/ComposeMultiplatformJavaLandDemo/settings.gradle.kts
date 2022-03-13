pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "ComposeMultiplatformJavaLandDemo"


include(":android")
include(":desktop")
include(":common")

