plugins {
    alias(libs.plugins.kanoyatech.android.library.compose)
    alias(libs.plugins.kotlin.serialization)
}

android { namespace = "com.kanoyatech.snapdex.ui" }

dependencies {
    // Core
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.appcompat)

    // Markdown
    implementation(libs.compose.markdown)

    // Koin
    implementation(libs.bundles.koin.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(projects.designsystem)
    implementation(projects.domain)
    implementation(projects.usecases)
}

ktfmt { kotlinLangStyle() }
