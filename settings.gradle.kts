pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}
rootProject.name = "otuskotlin-marketplace-202312"

include("m1l1-first")