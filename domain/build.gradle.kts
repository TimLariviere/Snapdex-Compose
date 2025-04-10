plugins { alias(libs.plugins.kanoyatech.jvm.library) }

dependencies { implementation(libs.kotlinx.coroutines.core) }

ktfmt { kotlinLangStyle() }
