plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("kapt") version "1.6.0"
    id("org.jetbrains.dokka") version "1.6.0"
    `maven-publish`
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io") {
        content {
            includeModule("com.github.jetbrains", "jetCheck")
        }
    }
    google {
        content {
            onlyForConfigurations("r8")
        }
    }
}

val r8 by configurations.creating

dependencies {
    kapt("com.google.auto.service:auto-service:1.0")
    implementation("com.google.auto.service:auto-service-annotations:1.0")
    testImplementation( "com.github.jetbrains:jetCheck:0.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    r8("com.android.tools:r8:3.0.73")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withSourcesJar()
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
}

val fatJar by tasks.registering(Jar::class) {
    dependsOn(configurations.runtimeClasspath, tasks.jar)
    archiveClassifier.set("fat")
    doFirst {
        configurations.runtimeClasspath.get().asFileTree.forEach { from(zipTree(it)) }
        from(zipTree(tasks.jar.get().archiveFile))
    }
    exclude("**/*.kotlin_metadata")
    exclude("**/*.kotlin_module")
    exclude("**/*.kotlin_builtins")
    exclude("**/module-info.class")
    exclude("META-INF/maven/**")
}

val embeddedJar by tasks.registering(JavaExec::class) {
    dependsOn(fatJar)
    inputs.file("proguard-rules.txt")
    outputs.files(layout.buildDirectory.file("libs/${project.name}-embedded.jar"))
    classpath(r8)
    mainClass.set("com.android.tools.r8.R8")
    args("--release", "--classfile")
    argumentProviders.add { listOf("--output", outputs.files.singleFile.toString()) }
    argumentProviders.add { listOf("--pg-conf", file("proguard-rules.txt").toString()) }
    argumentProviders.add { listOf("--lib", providers.systemProperty("java.home").get()) }
    argumentProviders.add { listOf(fatJar.get().archiveFile.get().asFile.toString()) }
}

val javadocJar by tasks.registering(Jar::class) {
    from(tasks.dokkaJavadoc)
    archiveClassifier.set("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks.named("sourcesJar"))
            artifact(embeddedJar)
            artifact(javadocJar)
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ephemient/utf-8b")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }

}
