plugins {
    kotlin("jvm") version "2.0.21"
    application
}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.inmo:tgbotapi:18.2.2")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "org.example.MainKt"
}