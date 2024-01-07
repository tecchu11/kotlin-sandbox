plugins {
    id("project.kotlin-convention")
    application
}

dependencies {
    // logging
    implementation(libs.bundles.logging)
    // coroutines
    implementation(libs.bundles.kotlin.coroutine)

    // retrofit
    implementation(libs.bundles.retrofit)

    // test
    testImplementation(libs.bundles.test)
}

application {
    mainClass.set("com.example.AppKt")
}
