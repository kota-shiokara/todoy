plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jmailen.kotlinter")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.starter.easylauncher")
}

android {
    namespace = "jp.ikanoshiokara.todoy"
    compileSdk = 34

    defaultConfig {
        applicationId = "jp.ikanoshiokara.todoy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

easylauncher {
    buildTypes {
        register("debug") {
            setFilters(chromeLike())
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation(platform("androidx.compose:compose-bom:2023.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Room
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("com.kiwi.navigation-compose.typed:core:0.7.0")

    val serializationVersion = "1.6.0"
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")

    // hilt
    val daggerHiltVersion = "2.48"
    implementation("com.google.dagger:hilt-android:$daggerHiltVersion")
    ksp("com.google.dagger:hilt-compiler:$daggerHiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}

kotlinter {
    ignoreFailures = false
    reporters = arrayOf("html")
}