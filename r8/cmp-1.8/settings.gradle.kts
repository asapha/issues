pluginManagement {
  repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    gradlePluginPortal()
    mavenCentral()
  }

  plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.9.0")
    kotlin("jvm").version(extra["kotlin.version"] as String)
    id("org.jetbrains.compose").version(extra["compose.version"] as String)
    id("org.jetbrains.kotlin.plugin.compose").version(extra["kotlin.version"] as String)
  }
}

rootProject.name = "r8-issue-repro"
