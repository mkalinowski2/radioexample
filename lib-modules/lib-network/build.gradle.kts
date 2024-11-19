plugins {
    id("kotlin")
}

dependencies {
    api(libs.coroutines)
    api(libs.retrofit)
    implementation(libs.koin.core)
    implementation(libs.json.moshi)
    implementation(libs.json.moshi.retrofit)
    implementation(libs.okhttp.logging)

    testImplementation(libs.koin.test)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.kotest)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.mock.webserver)
}
