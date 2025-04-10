import org.gradle.api.Plugin
import org.gradle.api.Project

class KtfmtConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("com.ncorti.ktfmt.gradle")
        }
    }
}