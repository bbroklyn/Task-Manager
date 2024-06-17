plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "ru.broklyn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.knowm.xchart:xchart:3.8.7")
    implementation("org.json:json:20240303")
    implementation("org.jetbrains.xodus:xodus-openAPI:2.0.1")
    implementation("org.jetbrains.xodus:xodus-entity-store:2.0.1")
    implementation("org.jetbrains.xodus:xodus-environment:2.0.1")
    implementation("org.jetbrains.xodus:xodus-vfs:2.0.1")
    implementation( "ch.qos.logback:logback-classic:1.5.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "ru.broklyn.taskmanager.Main"
        )
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}