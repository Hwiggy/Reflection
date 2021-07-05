plugins {
    kotlin("jvm") version "1.5.0"
    id("maven-publish")
    id("java-library")
}

group = "me.hwiggy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib"))
}

publishing {
    repositories {
        mavenLocal()
        when (project.findProperty("deploy") ?: "local") {
            "local" -> return@repositories
            "remote" -> maven {
                if (project.version.toString().endsWith("-SNAPSHOT")) {
                    setUrl("https://nexus.mcdevs.us/repository/mcdevs-snapshots/")
                    mavenContent { snapshotsOnly() }
                } else {
                    setUrl("https://nexus.mcdevs.us/repository/mcdevs-releases/")
                    mavenContent { releasesOnly() }
                }
                credentials {
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }
        }
    }
    publications {
        create<MavenPublication>("assembly") {
            from(components["java"])
        }
    }
}