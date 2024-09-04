import javax.xml.parsers.DocumentBuilderFactory

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

val mavenCredentials = getMavenCredentials("bazhard-dev-repository")

publishing {
    publications {
        create("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "snapshot"
            url = uri("https://repo.bazhard.dev/repository/maven-snapshots/")
            credentials {
                username = mavenCredentials?.first ?: ""
                password = mavenCredentials?.second ?: ""
            }
        }
        maven {
            name = "release"
            url = uri("https://repo.bazhard.dev/repository/maven-releases/")
            credentials {
                username = mavenCredentials?.first ?: ""
                password = mavenCredentials?.second ?: ""
            }
        }
    }
}

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

fun getMavenCredentials(serverId: String): Pair<String, String>? {
    val settingsFile = File(System.getProperty("user.home"), ".m2/settings.xml")
    if (!settingsFile.exists()) {
        return null
    }

    val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
    val document = documentBuilder.parse(settingsFile)

    val servers = document.getElementsByTagName("server")
    for (i in 0 until servers.length) {
        val server = servers.item(i)
        val id = server.childNodes.item(1).textContent
        if (id == serverId) {
            val username = server.childNodes.item(3).textContent
            val password = server.childNodes.item(5).textContent
            return username to password
        }
    }
    return null
}