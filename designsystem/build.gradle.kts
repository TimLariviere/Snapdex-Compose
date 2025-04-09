plugins {
    alias(libs.plugins.kanoyatech.android.library.compose)
}

android {
    namespace = "com.kanoyatech.snapdex.designsystem"
}

dependencies {
    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
}