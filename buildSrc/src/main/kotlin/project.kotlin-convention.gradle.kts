import com.example.ext.libs
import com.example.ext.of
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm")
    id("project.detekt-convention")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(libs.of("kotlin-coroutine-bom")))
    implementation(platform(libs.of("grpc-bom")))
    implementation(platform(libs.of("protobuf-bom")))
    implementation(platform(libs.of("koin-bom")))
    testImplementation(platform(libs.of("junit-bom")))
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

tasks.withType<KotlinJvmCompile> {
    jvmTargetValidationMode.set(JvmTargetValidationMode.WARNING)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
