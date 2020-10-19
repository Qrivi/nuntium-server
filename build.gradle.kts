import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dev.qrivi.fapp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.google.cloud.tools.jib") version "1.8.0"
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
    kotlin("plugin.jpa") version "1.4.10"
}

jib {
    from {
        image = "openjdk:11"
    }
    to {
        image = "qrivi/fappserver"
        tags = hashSetOf(version.toString(), "latest")
    }
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
        jvmFlags = listOf("-Dspring.profiles.active=production")
        ports = listOf("8080")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.hibernate:hibernate-core:5.4.22.Final")
    implementation("nl.basjes.parse.useragent:yauaa:5.19")
    implementation("io.jsonwebtoken:jjwt-api:0.11.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
        languageVersion = "1.4"
    }
}

tasks.register<Copy>("installGitHooks") {
    from("hooks")
    into(".git/hooks")
}

tasks.assemble {
    dependsOn(":installGitHooks", tasks.ktlintFormat)
}
