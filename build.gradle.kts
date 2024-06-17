val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinKtorVersion: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.5"
    kotlin("plugin.serialization") version "1.9.24"
}

group = "kubiakdev.com"
version = "0.0.2"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-config-yaml:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // AI
    implementation("dev.shreyaspatil.generativeai:generativeai-google:0.5.0-1.0.0")

    // Authentication
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koinKtorVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinKtorVersion")

    // OkHttp
    implementation("io.ktor:ktor-client-okhttp:2.3.9")

    // Mongo
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.1.0")

    // Rate limiting
    implementation("io.ktor:ktor-server-rate-limit:$ktorVersion")

    // Serialization
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    // Tests
    testImplementation("io.insert-koin:koin-test:$koinKtorVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinKtorVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}
