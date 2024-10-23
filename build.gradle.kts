plugins {
    kotlin("jvm") version "2.0.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation ("dev.inmo:tgbotapi:18.2.2")
}

kotlin {
    jvmToolchain(21)
}