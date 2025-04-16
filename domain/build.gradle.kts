plugins { alias(libs.plugins.kanoyatech.jvm.library) }

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    // JUnit
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.named<Test>("test") { useJUnitPlatform() }

ktfmt { kotlinLangStyle() }
