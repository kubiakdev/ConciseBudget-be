
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_ktor: String by project

plugins {
    kotlin("jvm") version "1.9.20"
    id("io.ktor.plugin") version "2.3.5"
    kotlin("plugin.serialization") version "1.9.20"
}

group = "kubiakdev.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:2.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Authentication
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")

    // OkHttp
    implementation("io.ktor:ktor-client-okhttp:1.6.6")

    // Mongo
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")

    // Rate limiting
    implementation("io.ktor:ktor-server-rate-limit:2.3.6")

    // Serialization
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Tests
    testImplementation("io.insert-koin:koin-test:$koin_ktor")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_ktor")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
