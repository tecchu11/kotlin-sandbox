import com.example.ext.libs
import com.example.ext.of
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.remove

plugins {
    id("project.kotlin-convention")
    id("com.google.protobuf")
}

protobuf {
    protoc {
        artifact = libs.of("protobuf-protoc").get().toString()
    }
    plugins {
        id("grpc") {
            artifact = libs.of("grpc-proto-gen-java").get().toString()
        }
        id("grpckt") {
            artifact = libs.of("grpc-proto-gen-kotlin").get().toString().plus(":jdk8@jar")
        }
        id("doc") {
            artifact = libs.of("protobuf-doc").get().toString()
        }
    }
    generateProtoTasks.all().forEach {
        it.plugins {
            id("grpc")
            id("grpckt")
            id("doc") {
                this.option("markdown,grpc-docs.md")
            }
        }
        it.builtins {
            id("kotlin")
        }
    }
}
