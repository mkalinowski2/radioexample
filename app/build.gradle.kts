plugins {
    id("com.android.application")
    id("pl.fewbits.radioexample.android")
    kotlin("plugin.serialization")
}

android {
    namespace = "pl.fewbits.radioexample"

    defaultConfig {
        applicationId = "pl.fewbits.radioexample"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":android-ui"))
    implementation(project(":android-app-navigator"))
    implementation(project(":feature-home"))
    implementation(project(":feature-radiodetails"))
    implementation(project(":component-radioplayer"))
    implementation(project(":core-home"))
    implementation(project(":lib-network"))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.navigation)
    implementation(libs.serializable)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
