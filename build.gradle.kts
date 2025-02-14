@file:Suppress("GradlePackageUpdate")

import com.netflix.graphql.dgs.codegen.CodeGen
import com.netflix.graphql.dgs.codegen.CodeGenConfig
import java.nio.file.Paths


buildscript {
    repositories {
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2") }
    }
}

plugins {
    id("org.springframework.boot") version "3.2.10"
    java
    id("nebula.release") version "19.0.10"
    id("nebula.maven-nebula-publish") version "18.2.0"
    id("com.bmuschko.docker-spring-boot-application") version "8.0.0"
    id("com.netflix.dgs.codegen").version("5.6.9").apply(false)
    id("org.owasp.dependencycheck") version "latest.release"
}

configure<nebula.plugin.release.git.base.ReleasePluginExtension> {
    defaultVersionStrategy = nebula.plugin.release.NetflixOssStrategies.SNAPSHOT(project)
}

dependencyCheck {
    analyzers.assemblyEnabled = false
    failBuildOnCVSS = 9.0F
    suppressionFile = "suppressions.xml"
    format = project.properties["dependencyCheckFormat"] as String? ?: "HTML"
}

group = "io.moderne"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases")
    }
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
    annotationProcessor("org.projectlombok:lombok:latest.release")
    
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2023.0.3"))
    implementation(platform("io.netty:netty-bom:4.1.100.Final"))
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform:latest.release"))

    implementation("org.openrewrite:rewrite-core:latest.release")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv")
    implementation("io.github.classgraph:classgraph:latest.release")
    implementation("org.projectlombok:lombok:latest.release")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.micrometer:micrometer-core:latest.release")

    implementation("com.graphql-java:graphql-java")
    implementation("com.graphql-java:graphql-java-extended-scalars")

    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-micrometer")

    implementation("io.micrometer:micrometer-registry-prometheus:latest.release")
    implementation("io.micrometer:micrometer-registry-prometheus-simpleclient:latest.release")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(platform("org.junit:junit-bom:latest.release"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("commons-io:commons-io:2.11.0")
    
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

afterEvaluate {
    println("I'm configuring ${project.name} with version ${project.version}")
}

extensions.findByName("buildScan")?.withGroovyBuilder {
    setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
    setProperty("termsOfServiceAgree", "yes")
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

tasks.withType<com.bmuschko.gradle.docker.tasks.image.Dockerfile> {
    instruction("RUN groupadd -r app && useradd --no-log-init -r -m -g app app && chown -R app:app /app")
    user("app")
}

docker {
    springBootApplication {
        images.set(listOf(
                "us.gcr.io/moderne-dev/moderne/${project.name}:${project.version}"
        ))
        baseImage.set("eclipse-temurin:17-jdk")
        jvmArgs.set(
                listOf(
                        "-XX:-OmitStackTraceInFastThrow",
                        "-XX:MaxRAMPercentage=65.0",
                        "-XX:MaxDirectMemorySize=2G",
                        "-XX:+HeapDumpOnOutOfMemoryError",
                        "-XX:+UseStringDeduplication"
                )
        )
    }
}

tasks.create<com.bmuschko.gradle.docker.tasks.image.DockerPushImage>("dockerPushImageGoogle") {
    dependsOn("dockerBuildImage")
    images.set(listOf("us.gcr.io/moderne-dev/moderne/${project.name}:${project.version}"))
    registryCredentials {
        url.set("https://us.gcr.io")
        username.set("_json_key")
        password.set(project.findProperty("GCR_KEY")?.toString())
    }
}

tasks.named("final") {
    dependsOn(tasks.getByName("dockerPushImage"))
}
