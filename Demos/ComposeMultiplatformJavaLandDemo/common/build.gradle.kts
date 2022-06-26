import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev725"
    id("com.android.library")
}

group = "de.mpmediasoft.polyspiral"
version = "1.0.0"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                implementation("de.mpmediasoft.polyspiral:MultiplatformJavaLandDemoModel:1.0.0-SNAPSHOT")
                implementation("io.github.microutils:kotlin-logging:2.1.23")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.4.2")
                api("androidx.core:core-ktx:1.8.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    namespace = "de.mpmediasoft.polyspiral.common"
}