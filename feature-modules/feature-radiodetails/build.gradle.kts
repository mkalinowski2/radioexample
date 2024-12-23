plugins {
    id("com.android.library")
    id("pl.fewbits.radioexample.android")
}

android {
    namespace = "pl.fewbits.radioexample.feature.radiodetails"
}

dependencies {
    implementation(project(":android-ui"))
    implementation(project(":android-app-navigator"))
    implementation(project(":core-home"))
    implementation(project(":component-radioplayer"))
}
