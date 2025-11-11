import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {
    const val ANDROID_TARGET_SDK_VERSION = 36
    const val ANDROID_MIN_SDK_VERSION = 26
    val JAVA_VERSION = JavaVersion.VERSION_1_8
    val JVM_TARGET = JvmTarget.JVM_1_8
}
