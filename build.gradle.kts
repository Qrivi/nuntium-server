val revision = System.getenv("GITHUB_RUN_NUMBER")

group = "dev.qrivi.nuntium"
version = "1.0.0-${if (revision.isNullOrBlank()) "SNAPSHOT" else "r$revision"}"

// Sources
repositories {
    mavenCentral()
}

// Plugins
plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.google.cloud.tools.jib") version "2.6.0"
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.liquibase.gradle") version "2.0.4"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("plugin.jpa") version "1.4.10"
}

// Dependencies
dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    // Hibernate
    implementation("org.hibernate:hibernate-core:5.4.22.Final")
    implementation("org.hibernate.validator:hibernate-validator:6.1.6.Final")
    implementation("org.hibernate.validator:hibernate-validator-annotation-processor:6.1.6.Final")
    runtimeOnly("org.postgresql:postgresql")
    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.0")
    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // YAUAA
    implementation("nl.basjes.parse.useragent:yauaa:5.19")
    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
    // Liquibase Plugin
    liquibaseRuntime("org.liquibase:liquibase-core:4.1.1")
    liquibaseRuntime("org.postgresql:postgresql")
    liquibaseRuntime("org.yaml:snakeyaml:1.27")
}

// Spring Boot config to make the build info available at runtime
springBoot {
    buildInfo()
}

// Config for the Liquibase plugin to manage the database
liquibase {
    activities.register("default") {
        this.arguments = mapOf(
            "defaultsFile" to "src/main/resources/liquibase.properties",
            "url" to System.getenv("SPRING_DATASOURCE_URL"),
            "username" to System.getenv("SPRING_DATASOURCE_USERNAME"),
            "password" to System.getenv("SPRING_DATASOURCE_PASSWORD")
        )
    }
    activities.register("dev") {
        this.arguments = mapOf(
            "defaultsFile" to "src/main/resources/liquibase-dev.properties"
        )
    }
    runList = project.properties["runList"]
}

// Config for the Google Jib plugin to build a Docker image
jib {
    from {
        image = "openjdk:14"
    }
    to {
        image = "qrivi/nuntium-server"
        tags = hashSetOf(version.toString(), "latest")
    }
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
        ports = listOf("8080")
    }
}

// Simple task that will copy project Git hooks to the .git directory
tasks.register<Copy>("installGitHooks") {
    from(".github/hooks")
    into(".git/hooks")
}

// Alternative command to run app with "dev" as active Spring profile
tasks.register("bootRunDev") {
    group = "application"
    description = "Runs this project as a Spring Boot application with the dev profile"
    doFirst {
        tasks.bootRun.configure {
            systemProperty("spring.profiles.active", "dev")
        }
    }
    finalizedBy("bootRun")
}

// Kotlin compiler arguments and chaining installGitHooks task
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        languageVersion = "1.4" // Kotlin language version
        jvmTarget = "14" // JVM language version
        freeCompilerArgs = listOf("-Xjsr305=strict") // strict null-safety
    }
    finalizedBy("installGitHooks") // install most recent Git hooks
}

// Use JUnit for tests
tasks.withType<Test> {
    useJUnitPlatform()
}
