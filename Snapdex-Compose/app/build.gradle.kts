import androidx.room.gradle.RoomExtension
import java.util.Properties

val signingPropertiesFile = rootProject.file("signing.properties")
val signingProperties = Properties()

if (signingPropertiesFile.exists()) {
    signingProperties.load(signingPropertiesFile.inputStream())
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

android {
    val projectApplicationId = "com.kanoyatech.snapdex"

    namespace = projectApplicationId
    compileSdk = 35

    defaultConfig {
        applicationId = projectApplicationId
        minSdk = 26 // Android Oreo required for variable font
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    androidResources {
        generateLocaleConfig = true
    }

    signingConfigs {
        create("release") {
            if (signingPropertiesFile.exists()) {
                storeFile = file(signingProperties["storeFile"] as String)
                storePassword = signingProperties["storePassword"] as String
                keyAlias = signingProperties["keyAlias"] as String
                keyPassword = signingProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

extensions.configure<RoomExtension> {
    schemaDirectory("$projectDir/schemas")
}

tasks.register("buildAllRelease") {
    dependsOn("assembleRelease", "bundleRelease")
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.play.services)

    // Koin
    implementation(libs.bundles.koin.compose)

    // Compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(platform(libs.androidx.compose.bom))

    // Markdown
    implementation(libs.compose.markdown)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // DataStore
    implementation(libs.datastore.preferences)

    // Splashscreen
    implementation(libs.androidx.core.splashscreen)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    "ksp"(libs.room.compiler)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Tensorflow Lite
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)

    // Ktor
    implementation(libs.bundles.ktor)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.bundles.compose.debug)
}