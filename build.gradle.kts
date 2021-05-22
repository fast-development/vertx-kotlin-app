import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin ("jvm")
  application
  id("com.github.johnrengelman.shadow")
}

group = "io.abner.kotlin.vertx"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenCentral()
}

val vertxVersion = "4.1.0.CR1"
val junitJupiterVersion = "5.7.0"

val mainVerticleName = "io.abner.kotlin.vertx.MainVerticle"
// val launcherClassName = "io.vertx.core.Launcher"
val launcherClassName = "io.abner.kotlin.vertx.ApplicationLauncher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
  mainClassName = launcherClassName
}

dependencies {
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  // WEB
  implementation("io.vertx:vertx-web-client")
  implementation("io.vertx:vertx-web-validation")

  implementation("io.vertx:vertx-json-schema")
  implementation("io.vertx:vertx-shell")

  // CONFIGURATION
  implementation("io.vertx:vertx-config")

  // METRICS AND TELEMETRY
  implementation("io.vertx:vertx-micrometer-metrics")
  implementation("io.vertx:vertx-opentelemetry")
  implementation("io.micrometer:micrometer-registry-prometheus:1.7.0")
  implementation("io.micrometer:micrometer-registry-jmx:1.7.0")

  // PERSISTENCE
  implementation("io.vertx:vertx-redis-client")
  implementation("io.vertx:vertx-pg-client")

  // RESILIENCE
  implementation("io.vertx:vertx-health-check")
  implementation("io.vertx:vertx-circuit-breaker")

  // LANGUAGE
  implementation("io.vertx:vertx-lang-kotlin-coroutines")
  implementation("io.vertx:vertx-lang-kotlin")
  implementation(kotlin("stdlib-jdk8"))


  testImplementation("io.vertx:vertx-junit5")
  testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "11"

tasks.withType<ShadowJar> {
  archiveClassifier.set("fat")
  manifest {
    attributes(
      mapOf(
        "Main-Verticle" to mainVerticleName
      )
    )
    attributes
  }
   mergeServiceFiles {
     include("META-INF/services/io.vertx.core.spi.VerticleFactory")

   }

}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events = setOf(PASSED, SKIPPED, FAILED)
  }
}

tasks.withType<JavaExec> {
  args = listOf("run", mainVerticleName, "--redeploy=$watchForChange", "--launcher-class=$launcherClassName", "--on-redeploy=$doOnChange")
}
