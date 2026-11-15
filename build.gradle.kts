plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.kotlinter) apply false
}

subprojects {
    apply(plugin = "org.jmailen.kotlinter")

    // 生成コード (Compose Resources / Room / KSP) は lint 対象から外す
    tasks.withType<org.jmailen.gradle.kotlinter.tasks.LintTask>().configureEach {
        exclude { it.file.path.contains("${File.separator}build${File.separator}") }
    }
    tasks.withType<org.jmailen.gradle.kotlinter.tasks.FormatTask>().configureEach {
        exclude { it.file.path.contains("${File.separator}build${File.separator}") }
    }
}
