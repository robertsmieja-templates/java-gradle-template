import org.apache.commons.lang3.SystemUtils

plugins {
    // Apply the java-library plugin to add support for Java Library
    `java-library`
    id("org.ajoberstar.reckon") version "0.12.0"
    id("com.diffplug.gradle.spotless") version "4.0.1"
    id("com.github.spotbugs") version "4.2.4"
    id("com.google.cloud.tools.jib") version "2.3.0"
    id("com.star-zero.gradle.githook") version "1.2.0"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

dependencies {
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    // api("org.apache.commons:commons-math3:3.6.1")

    spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.7.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:28.1-jre")

    // Use JUnit Jupiter API for testing.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")

    // Use JUnit Jupiter Engine for testing.
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

/**
 * Plugin configuration
 */

githook {
    failOnMissingHooksDir = false
    createHooksDirIfNotExist = true

    // Gradle git-hooks plugin does not handle directories with spaces on Windows
    if (!SystemUtils.IS_OS_WINDOWS) {
        hooks {
            create("pre-commit") {
                task = "check"
            }
        }
    }
}

reckon {
    scopeFromProp()
    stageFromProp("milestone", "rc", "final")
}

spotbugs {
    // effort = "max" does not work, revisit in a latter version of SpotBugs
    this.setEffort("max")
}

spotless {
    java {
        // custom("Lambda fix") {
        //      it.replace("} )", "})").replace("} ,", "},")
        // }

        endWithNewline()
        removeUnusedImports()
        trimTrailingWhitespace()

        googleJavaFormat()
    }
}

/**
 * Task configurations
 */

val test by tasks.getting(Test::class) {
    // Use junit platform for unit tests
    useJUnitPlatform()
}

val spotlessApply = tasks.spotlessApply
val spotlessCheck = tasks.spotlessCheck {
    shouldRunAfter(spotlessApply)
}

tasks.check {
    dependsOn(spotlessApply)
}
