plugins {
    id("pl.fewbits.radioexample.java")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(project(":lib-network"))
    implementation(libs.koin.core)
    implementation(libs.serializable)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockk)
    testImplementation(libs.test.kotest)
    testImplementation(libs.test.mock.webserver)
    testImplementation(libs.koin.test)
}
