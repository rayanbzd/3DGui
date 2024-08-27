plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("maven-publish")
}

group = "dev.bazhard.library"
version = "1.0.0-SNAPSHOT"
description = "A 3D GUI library for Minecraft plugins"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION // Use the Mojang production mappings

repositories {
    mavenCentral()
    maven( "https://repo.dmulloy2.net/repository/public/") // Required for ProtocolLib
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0-SNAPSHOT")
}

tasks {
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("**/*.yml") { // Filter all .yml files
            expand(project.properties)
        }
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release = 21
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}