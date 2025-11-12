plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "kokoro.multiplatform.sasanqua"
version = "1.0.0"
application {
    mainClass.set("kokoro.multiplatform.sasanqua.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.koin.ktor)
    implementation(libs.logback)
    implementation(libs.mysql.connector.java)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.ktor.serverStatusPages)
    implementation(libs.ktor.serverAuthJwt)
    implementation(libs.ktor.serverCors)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.default.headers)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
}