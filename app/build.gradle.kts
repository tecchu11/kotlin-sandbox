import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    application
}

repositories {
    mavenCentral()
}

java.targetCompatibility = JavaVersion.VERSION_17

dependencies {
    // logging
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    // coroutines
    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.7.3"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")

    // retrofit
    val retrofit = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-jackson:$retrofit")

    // jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
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
