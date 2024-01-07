import com.example.ext.libs
import com.example.ext.of
import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm")
    id("project.respository-convention")
    id("project.detekt-convention")
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinJvmCompile> {
    jvmTargetValidationMode.set(JvmTargetValidationMode.WARNING)
}

dependencies {
    implementation(platform(libs.of("kotlin-coroutine-bom")))
    implementation(platform(libs.of("grpc-bom")))
    implementation(platform(libs.of("proto-bom")))
    testImplementation(platform(libs.of("junit-bom")))
}
