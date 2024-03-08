pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "otus-webchat"

includeBuild("otus-webchat-messages-be")
includeBuild("otus-webchat-messages-fe")
includeBuild("otus-webchat-message-be")
includeBuild("otus-webchat-rooms-be")
includeBuild("otus-webchat-rooms-fe")
includeBuild("otus-webchat-web")