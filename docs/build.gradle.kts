plugins {
    id("com.eden.orchidPlugin") version "0.20.0"
    `arcana-base`
}

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    orchidRuntimeOnly(Orchid.bundles.docs)
    orchidRuntimeOnly(Orchid.themes.copper)
    orchidRuntimeOnly(Orchid.plugins.github)
    orchidRuntimeOnly(Orchid.plugins.kotlindoc)
    orchidRuntimeOnly(Orchid.plugins.pluginDocs)
}

// Orchid setup
//----------------------------------------------------------------------------------------------------------------------

val ghUser: String by extra
val ghToken: String by extra
var releaseVersion: String by project.extra

orchid {
    githubToken = ghToken
    version = releaseVersion
    args = listOf("--experimentalSourceDoc")
}

val build by tasks
val check by tasks
val orchidBuild by tasks
val orchidDeploy by tasks

orchidBuild.mustRunAfter(check)
build.dependsOn(orchidBuild)

val publish by tasks.registering {
    dependsOn(orchidDeploy)
}
