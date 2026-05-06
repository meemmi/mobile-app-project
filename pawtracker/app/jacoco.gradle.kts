

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
