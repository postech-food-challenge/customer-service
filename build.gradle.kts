val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val exposedVersion: String by project
val hikaricpVersion: String by project
val postgresqlVersion: String by project
val mockkVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("org.sonarqube") version "4.4.1.3373"
    id("jacoco")
}

group = "br.com.fiap.postech"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sonar {
    properties {
        property("sonar.gradle.skipCompile", "true")
        property("sonar.projectKey", "postech-food-challenge_order-service")
        property("sonar.organization", "postech-food-challenge")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property(
            "sonar.coverage.exclusions",
            """
                **/br/com/fiap/postech/Application.kt,
                **/br/com/fiap/postech/domain/**,
                **/br/com/fiap/postech/configuration/**,
                **/br/com/fiap/postech/infrastructure/**
            """.trimIndent()
        )
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")

    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(
                    "**/br/com/fiap/postech/domain/**",
                    "**/br/com/fiap/postech/configuration/**",
                    "**/br/com/fiap/postech/infrastructure/**"
                )
            }
        })
    )
}