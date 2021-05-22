rootProject.name = "vertxapp"


pluginManagement {
  val kotlinVersion: String by settings
  val shadowVersion: String by settings
  plugins {
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("com.github.johnrengelman.shadow") version shadowVersion
  }
}
