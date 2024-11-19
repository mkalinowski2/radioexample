import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class JavaPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.applyPlugins()
        configure(project)
    }

    fun configure(project: Project) {
        project.plugins.toMutableList().forEach { plugin ->
            when (plugin) {
                is JavaLibraryPlugin -> {
                    project.extensions.getByType(JavaPluginExtension::class.java).apply {
                        sourceCompatibility = Config.JAVA_VERSION
                        targetCompatibility = Config.JAVA_VERSION
                        withJavadocJar()
                        withSourcesJar()
                    }
                }
            }
        }

        project.tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile::class.java) {
            compilerOptions {
                jvmTarget.set(Config.JVM_TARGET)
            }
        }
    }

    private fun Project.applyPlugins() {
        plugins.apply("java-library")
        plugins.apply("kotlin")
    }
}
