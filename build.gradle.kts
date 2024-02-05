plugins {
    val kotlinVersion = "1.9.0"
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion apply false

    id("com.android.application") version "8.1.2" apply false
    id("org.jmailen.kotlinter") version "3.13.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    id("com.starter.easylauncher") version "6.2.0" apply false
}