import org.gradle.testing.jacoco.tasks.JacocoReport
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val excludes = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*"
    )

    classDirectories.setFrom(
        fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug")) {
            exclude(excludes)
        }
    )

    sourceDirectories.setFrom(files(
        "src/main/java",
        "src/main/kotlin"
    ))

    executionData.setFrom(fileTree(buildDir) {
        include("**/*.exec")
    })
}