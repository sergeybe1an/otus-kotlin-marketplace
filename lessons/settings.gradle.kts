pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}
rootProject.name = "otuskotlin-marketplace-202312"

include("m1l1-first")
include("m1l3-func")
include("m1l4-opp")
include("m1l5-dsl")
include("m2l1-coroutines")
include("m2l2-flows")