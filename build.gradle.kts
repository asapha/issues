import kotlin.io.extension

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
}

repositories {
  mavenCentral()
  google()
  maven(url = "https://storage.googleapis.com/r8-releases/raw")
}

// Initial r8 setup from https://github.com/TWiStErRob/repros/tree/main/r8/fastutil-invokespecial-indirect-superinterface

val r8Deps: Configuration = configurations.dependencyScope("r8").get()

dependencies {
  implementation(compose.desktop.currentOs)
  r8Deps("com.android.tools:r8:8.2.47")
}

compose.desktop {
  application {
    mainClass = "MainKt"
  }
}

kotlin {
  jvmToolchain(17)
}

val launcher = javaToolchains.launcherFor {
  languageVersion.set(JavaLanguageVersion.of(17))
}

val fatJar = tasks.register<Jar>("fatJar") {
  manifest {
    attributes(
      "Main-Class" to "MainKt"
    )
  }
  archiveClassifier = "fat"
  dependsOn(configurations.runtimeClasspath)
  from(configurations.runtimeClasspath.map { it.map(::zipTree) })
  with(tasks.jar.get())
  exclude(
    // JVM metadata.
    "**/module-info.class",
    // Kotlin metadata.
    "**/*.kotlin_builtins",
    "**/*.kotlin_module",
    "**/*.kotlin_metadata",
    // Maven metadata.
    "META-INF/maven/**",
  )
  duplicatesStrategy = DuplicatesStrategy.FAIL
}

val r8: Provider<out Configuration> = configurations.resolvable("r8RuntimeClasspath") {
  extendsFrom(r8Deps)
}

val r8Jar = tasks.register<JavaExec>("r8Jar") {
  val r8File: Provider<RegularFile> = base.libsDirectory.flatMap { libs ->
    libs.file(base.archivesName.map { "${it}-r8.jar" })
  }
  val rulesFile = layout.projectDirectory.file("src/main/r8.txt")
  val configFile = base.libsDirectory.file("r8-config.txt")
  val fatJarFile = fatJar.flatMap { it.archiveFile }
  inputs.file(fatJarFile)
    .withPropertyName("fatJarFile")
    .withPathSensitivity(PathSensitivity.NONE)
  inputs.file(rulesFile)
    .withPropertyName("rulesFile")
    .withPathSensitivity(PathSensitivity.NONE)
    .normalizeLineEndings()
  outputs.file(r8File)
  outputs.file(configFile)

  // R8 uses the executing JDK to determine the classfile target.
//  javaLauncher = javaLauncher.get().metadata.installationPath.asFile.absolutePath
  javaLauncher = launcher
  maxHeapSize = "1G"

  classpath(r8)
  mainClass = "com.android.tools.r8.R8"
  args = listOf(
    "--release",
    "--classfile",
    "--pg-conf", rulesFile.asFile.absolutePath,
    "--pg-conf-output", configFile.get().asFile.absolutePath,
    "--output", r8File.get().asFile.absolutePath,
    "--lib", javaLauncher.get().metadata.installationPath.asFile.absolutePath.also {
      println("Using JDK: $it")
    },
    fatJarFile.get().asFile.absolutePath,
  )
}

val runR8 = tasks.register<JavaExec>("runR8") {
  val jar =
    r8Jar.map { it.outputs.files.filter { it.extension == "jar" }.singleFile }

  inputs.files(jar)

  mainClass.set("MainKt")
  classpath = files(jar)
}