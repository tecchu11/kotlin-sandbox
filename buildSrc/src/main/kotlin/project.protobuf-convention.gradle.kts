import com.example.ext.libs
import com.example.ext.of

plugins {
    id("project.kotlin-convention")
    id("com.google.protobuf")
}

protobuf {
    protoc {
        artifact = libs.of("protobuf-protoc").get().toString()
    }
    plugins {
        create("grpc") {
            artifact = libs.of("grpc-proto-gen-java").get().toString()
        }
        create("grpckt") {
            artifact = libs.of("grpc-proto-gen-kotlin").get().toString().plus(":jdk8@jar")
        }
    }
    generateProtoTasks.all().forEach {
        it.plugins {
            create("grpc")
            create("grpckt")
        }
        it.builtins {
            create("kotlin")
        }
    }
}
