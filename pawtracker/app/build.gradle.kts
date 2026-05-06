
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    id("jacoco")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

android {
    namespace = "com.example.pawtracker"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.pawtracker"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["MAPS_API_KEY"] = localProperties.getProperty("MAPS_API_KEY") ?: ""

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }


    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:5.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.text)
    implementation(libs.androidx.compose.material3.window.size.class1)
    ksp(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("junit:junit:4.13.2")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.compose.material3:material3-window-size-class")
}

jacoco {
    toolVersion = "0.8.10"
}

// unpack classes.jar
tasks.register<Copy>("unpackJacocoClasses") {

    dependsOn("bundleDebugClassesToCompileJar")

    val classesJar = layout.buildDirectory.file(
        "intermediates/compile_app_classes_jar/debug/bundleDebugClassesToCompileJar/classes.jar"
    )

    val outputDir = layout.buildDirectory.dir("tmp/jacoco-unpacked-classes")

    from(zipTree(classesJar))
    into(outputDir)
}
//FULL coverage report (unit + UI tests)
tasks.register<JacocoReport>("jacocoFullReport") {

    dependsOn(
        "testDebugUnitTest",              // Unit tests
        "connectedDebugAndroidTest",      // UI tests
        "unpackJacocoClasses"             // Extract classes
    )

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "**/*\$Lambda$*.*",
        "**/*\$inlined$*.*"
    )

    val unpackedDir = layout.buildDirectory.dir("tmp/jacoco-unpacked-classes")

    classDirectories.setFrom(
        fileTree(unpackedDir) {
            include(
                "**/com/example/pawtracker/ui/**",
                "**/com/example/pawtracker/data/repository/**",
                "**/com/example/pawtracker/ui/theme/**"
            )

            //  don't want counted
            exclude(
                "**/com/example/pawtracker/ui/components/**",
                "**/com/example/pawtracker/mapper/**",
                "**/com/example/pawtracker/utils/**",
                "**/com/example/pawtracker/data/local/**",
                "**/com/example/pawtracker/domain/**",
                "**/com/example/pawtracker/converters/**",
                "**/com/example/pawtracker/preferences/**",

                // Android auto-generated
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",

                // Test classes
                "**/*Test*.*",
                "**/*\$Lambda$*.*",
                "**/*\$inlined$*.*"
            )
        }
    )

    sourceDirectories.setFrom(
        files("src/main/java", "src/main/kotlin")
    )

    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include(
                // Unit test coverage
                "jacoco/testDebugUnitTest.exec",

                // UI test coverage
                "outputs/code_coverage/debugAndroidTest/connected/**/*.ec"
            )
        }
    )
}


