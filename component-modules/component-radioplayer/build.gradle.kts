plugins {
    id("com.android.library")
    id("pl.fewbits.radioexample.android")
}

android {
    namespace = "pl.fewbits.radioexample.component.radioplayer"
}

dependencies {
    implementation(project(":android-ui"))
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-session:1.2.0")
}
