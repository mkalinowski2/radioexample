plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":lib-network"))
    implementation(libs.koin.core)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.kotest)
    testImplementation(libs.test.mock.webserver)
    testImplementation(libs.koin.test)
}
