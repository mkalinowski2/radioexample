plugins {
    id("com.android.library")
    id("pl.fewbits.radioexample.android")
    kotlin("plugin.serialization")
}

android {
    namespace = "pl.fewbits.radioexample.android.appnavigator"
}

dependencies {
    implementation(project(":core-home"))
    implementation(project(":android-ui"))
    implementation(libs.serializable)

}
