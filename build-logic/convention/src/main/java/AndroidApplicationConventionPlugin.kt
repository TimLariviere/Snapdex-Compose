
import com.android.build.api.dsl.ApplicationExtension
import com.kanoyatech.buildlogic.convention.ExtensionType
import com.kanoyatech.buildlogic.convention.configureBuildTypes
import com.kanoyatech.buildlogic.convention.configureKotlinAndroid
import com.kanoyatech.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                val projectApplicationId = libs.findVersion("projectApplicationId").get().toString()

                namespace = projectApplicationId

                defaultConfig {
                    applicationId = projectApplicationId
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                androidResources {
                    generateLocaleConfig = true
                }

                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.APPLICATION)
            }
        }
    }
}