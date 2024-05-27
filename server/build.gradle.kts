plugins {
    application
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    idea
}

group = "swifties.testapp"
version = "1.0.0"

application {
    mainClass.set("swifties.testapp.ApplicationKt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.jdbc)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jjwt.api)
    implementation(libs.jjwt.impl)
    implementation(libs.jjwt.jackson)
    runtimeOnly(libs.postgresql)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.spring.security.test)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
