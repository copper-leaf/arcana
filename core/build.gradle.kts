plugins {
    id("com.android.library")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    kotlin("multiplatform") version "1.3.72"
    `arcana-base`
}

val ghUser: String by extra
val ghToken: String by extra

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = project.version.toString()
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        val release by getting {
            isMinifyEnabled = false
        }
    }
    sourceSets {
        val main by getting {
            setRoot("src/androidMain")
        }
        val androidTest by getting {
            setRoot("src/androidTest")
        }
    }
    testOptions {
        unitTests(delegateClosureOf<com.android.build.gradle.internal.dsl.TestOptions.UnitTestOptions> {
            setIncludeAndroidResources(true)
            setReturnDefaultValues(true)
        })
    }
    lintOptions {
        disable("GradleDependency")
    }
}

kotlin {
    jvm { }
    android {
        publishAllLibraryVariants()
    }
    js {
        browser { }
    }
    ios { }

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }

        // Common Sourcesets
        val commonMain by getting {
            dependencies {
                implementation(Kotlin.stdlib.common)
                implementation(KotlinX.coroutines.coreCommon)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-io:0.1.16")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(Kotlin.test.common)
                implementation(Kotlin.test.annotationsCommon)
            }
        }

        // plain JVM Sourcesets
        val jvmMain by getting {
            dependencies {
                implementation(Kotlin.stdlib.jdk7)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(Kotlin.test.junit)
            }
        }

        // Android JVM Sourcesets
        val androidMain by getting {
            dependsOn(jvmMain)
            dependencies {
                implementation(Kotlin.stdlib.jdk7)
            }
        }
        val androidTest by getting {
            dependsOn(jvmTest)
            dependencies {
                implementation(Kotlin.test.junit)
            }
        }

        // JS Sourcesets
        val jsMain by getting {
            dependencies {
                implementation(Kotlin.stdlib.js)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(Kotlin.test.js)
            }
        }

        // iOS Sourcesets
        val iosMain by getting {
            dependencies { }
        }
        val iosTest by getting {
            dependencies { }
        }
    }
}

afterEvaluate {
    publishing {
        repositories {
            // publish to the project buildDir to make sure things are getting published correctly
            maven(url = "${project.buildDir}/.m2/repository") {
                name = "project"
            }
            maven(url = "https://maven.pkg.github.com/copper-leaf/arcana") {
                name = "GitHubPackages"
                credentials {
                    username = ghUser
                    password = ghToken
                }
            }
        }
    }
}

ktlint {
    debug.set(false)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)
    additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

tasks.withType<Test> {
    testLogging {
        showStandardStreams = true
    }
}
