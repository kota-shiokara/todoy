import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * todoy の全 KMP モジュール共通の設定。
 *
 * ターゲット: Android / iOS (arm64, simulatorArm64) / Desktop (JVM) / Web (wasmJs)
 *
 * Android の namespace はモジュールのパスから `jp.ikanoshiokara.todoy.<module>` として自動で決まる。
 */
class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            pluginManager.apply("com.android.kotlin.multiplatform.library")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KotlinMultiplatformExtension> {
                extensions.configure<KotlinMultiplatformAndroidLibraryTarget>("android") {
                    namespace = "jp.ikanoshiokara.todoy." + path.removePrefix(":").replace(":", ".")
                    compileSdk = libs.intVersion("android-compileSdk")
                    minSdk = libs.intVersion("android-minSdk")
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }

                iosArm64()
                iosSimulatorArm64()

                jvm {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
                    }
                }

                @OptIn(ExperimentalWasmDsl::class)
                wasmJs {
                    browser()
                }

                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
}

private fun VersionCatalog.intVersion(alias: String): Int = findVersion(alias).get().requiredVersion.toInt()
