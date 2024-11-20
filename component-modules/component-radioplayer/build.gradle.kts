plugins {
    id("com.android.library")
    id("pl.fewbits.radioexample.android")
}

android {
    namespace = "pl.fewbits.radioexample.component.radioplayer"
}

dependencies {
    implementation(project(":android-ui"))
    implementation(libs.media.exoplayer)
    implementation(libs.media.session)
}
