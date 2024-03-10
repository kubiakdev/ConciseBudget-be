
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_ktor: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.5"
    kotlin("plugin.serialization") version "1.9.23"
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
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    // Authentication
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")

    // OkHttp
    implementation("io.ktor:ktor-client-okhttp:2.3.9")

    // Mongo
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.0.0")

    // Rate limiting
    implementation("io.ktor:ktor-server-rate-limit:$ktor_version")

    // Serialization
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Tests
    testImplementation("io.insert-koin:koin-test:$koin_ktor")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_ktor")
    testImplementation("io.ktor:ktor-server-tests-jvm:$koin_ktor")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
