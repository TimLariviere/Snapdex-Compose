plugins { alias(libs.plugins.kanoyatech.jvm.library) }

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)

    implementation(projects.snapdex.domain)
}

ktfmt { kotlinLangStyle() }
