import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin

class AndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.applyPlugins()
        JavaPlugin().configure(project)

        project.plugins.toMutableList().forEach { plugin ->
            when (plugin) {
                is BasePlugin -> {
                    val extension = project.extensions.getByType(BaseExtension::class.java)
                    project.configureAndroidModule(extension)
                }
            }
        }
    }

    private fun Project.configureAndroidModule(android: BaseExtension) = android.apply {
        sourceSets.configureEach {
            java.srcDirs("src/$name/kotlin")
        }
        setCompileSdkVersion(Config.ANDROID_TARGET_SDK_VERSION)
        defaultConfig {
            targetSdk = Config.ANDROID_TARGET_SDK_VERSION
            minSdk = Config.ANDROID_MIN_SDK_VERSION
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = Config.JAVA_VERSION
            targetCompatibility = Config.JAVA_VERSION
        }

        buildFeatures.compose = true
    }

    private fun Project.applyPlugins() {
        plugins.apply("kotlin-android")
        plugins.apply("kotlin-parcelize")
        plugins.apply("org.jetbrains.kotlin.plugin.compose")
    }
}
