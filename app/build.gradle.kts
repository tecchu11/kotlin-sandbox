plugins {
    id("project.kotlin-convention")
    id("project.protobuf-convention")
    application
}

dependencies {
    //gRPC
    implementation(libs.bundles.grpc.kotlin)
    testImplementation(libs.grpc.testing)
    // logging
    implementation(libs.bundles.logging)
    // coroutines
    implementation(libs.bundles.kotlin.coroutine)
    // test
    testImplementation(libs.bundles.test)
}

application {
    mainClass.set("com.example.AppKt")
}
