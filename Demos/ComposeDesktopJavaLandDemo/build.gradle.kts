import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}

group = "de.mpmediasoft.compose"
version = "1.0.0"

repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

val osName = System.getProperty("os.name")
val targetOs = when {
    osName == "Mac OS X" -> "mac"
    osName.startsWith("Win") -> "win"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported OS: $osName")
}

val osArch = System.getProperty("os.arch")
var targetArch = when (osArch) {
    "x86_64", "amd64" -> ""
    "aarch64" -> "-aarch64"
    else -> error("Unsupported arch: $osArch")
}

val jfxClassifier = "${targetOs}${targetArch}"
val jfxVersion = "18-ea+9"

dependencies {
    testImplementation(kotlin("test"))
    implementation(compose.desktop.currentOs)
    implementation("org.openjfx:javafx-base:$jfxVersion:$jfxClassifier")
    implementation("org.openjfx:javafx-graphics:$jfxVersion:$jfxClassifier")
    implementation("org.openjfx:javafx-controls:$jfxVersion:$jfxClassifier")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ComposeDesktopJavaLandDemo"
            packageVersion = "1.0.0"
        }
    }
}