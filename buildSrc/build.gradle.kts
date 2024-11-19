plugins {
    `kotlin-dsl`
}

dependencies {
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("com.android.tools.build:gradle:8.4.0")
    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.0.0")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())
}
