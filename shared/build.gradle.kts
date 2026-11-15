plugins {
    alias(libs.plugins.todoy.kmp.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.room.runtime)
            api(libs.kotlinx.coroutines.core)
            api(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.core)
        }
        androidMain.dependencies {
            implementation(libs.sqlite.bundled)
            implementation(libs.koin.android)
        }
        iosMain.dependencies {
            implementation(libs.sqlite.bundled)
        }
        jvmMain.dependencies {
            implementation(libs.sqlite.bundled)
        }
        jvmTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    listOf(
        "kspAndroid",
        "kspIosArm64",
        "kspIosSimulatorArm64",
        "kspJvm",
        "kspWasmJs",
    ).forEach { add(it, libs.room.compiler) }
}
