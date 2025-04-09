import java.util.Properties

val signingPropertiesFile = rootProject.file("signing.properties")
val signingProperties = Properties()

if (signingPropertiesFile.exists()) {
    signingProperties.load(signingPropertiesFile.inputStream())
}

plugins {
    alias(libs.plugins.kanoyatech.android.application.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.kotlin.serialization)
}

android {
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
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

tasks.register("buildAllRelease") {
    dependsOn("assembleRelease", "bundleRelease")
}

dependencies {
    // Core
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.bundles.koin.compose)

    // Splashscreen
    implementation(libs.androidx.core.splashscreen)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(projects.snapdex.designsystem)
    implementation(projects.snapdex.domain)
    implementation(projects.snapdex.data)
    implementation(projects.snapdex.ui)
}