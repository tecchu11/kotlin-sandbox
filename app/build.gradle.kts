import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    application
}

repositories {
    mavenCentral()
}

java.targetCompatibility = JavaVersion.VERSION_17

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClass.set("com.example.AppKt")
}

tasks.named<KotlinCompile>("compileKotlin") {
    kotlinOptions.jvmTarget = "17"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
