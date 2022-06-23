plugins {
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev724"
    id("com.android.application")
    kotlin("android")
}

group = "de.mpmediasoft.polyspiral"
version = "1.0.0"

repositories {

}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.4.0")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "de.mpmediasoft.polyspiral.android"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    namespace = "de.mpmediasoft.polyspiral.android"
}