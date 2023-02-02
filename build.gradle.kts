@file:Suppress("GradlePackageUpdate")

import com.netflix.graphql.dgs.codegen.CodeGen
import com.netflix.graphql.dgs.codegen.CodeGenConfig
import java.nio.file.Paths

plugins {
    java
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"

    id("com.netflix.dgs.codegen").version("latest.release").apply(false)
}

group = "io.moderne"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

configurations {
    all {
        resolutionStrategy {
            cacheChangingModulesFor(0, TimeUnit.SECONDS)
            cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

open class GraphqlGenerationTask : DefaultTask() {
    @TaskAction
    fun generate() {
        val config = CodeGenConfig(
                schemaFiles = setOf(project.file("${project.projectDir}/src/main/resources/schema")),
                outputDir = project.file("${project.buildDir}/generated").toPath(),
                examplesOutputDir = Paths.get("${project.buildDir}/generated-examples"),
                writeToFiles = true,
                packageName = "io.moderne.organizations",
                generateClientApi = false
        )

        CodeGen(config).generate()
    }
}

tasks.register<GraphqlGenerationTask>("generateGraphqlJava")

tasks.register<Copy>("copyGeneratedGraphql") {
    from("$buildDir/generated")
    include("**/*.java")
    into("$projectDir/src/main/java")
}
