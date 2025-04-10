plugins {
    alias(libs.plugins.kanoyatech.android.library)
    alias(libs.plugins.kanoyatech.android.room)
    alias(libs.plugins.kanoyatech.jvm.ktor)
    alias(libs.plugins.kotlin.serialization)
}

android { namespace = "com.kanoyatech.snapdex.data" }

ktfmt { kotlinLangStyle() }

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    // Koin
    implementation(libs.bundles.koin)

    // DataStore
    implementation(libs.datastore.preferences)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Tensorflow Lite
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)

    implementation(projects.snapdex.domain)
}

ktfmt { kotlinLangStyle() }
