import java.util.Properties

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val localProperties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.google.services)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm("desktop") {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.splash.screen)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.auth.kmp)
            implementation(libs.auth.firebase.kmp)
            implementation(libs.firebase.app)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.storage)

            implementation(libs.kotlinx.serialization)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.messagebar.kmp)

            api(libs.kmp.notifier)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
        kotlin.sourceSets.getByName("desktopMain").kotlin.srcDir(project.layout.buildDirectory.dir("generated/sources/secrets/desktop/main"))
    }
}

android {
    namespace = "com.ash.bingemaster"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ash.bingemaster"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        // Retrieve the API key from local.properties
        val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY", "")
        // Retrieve the Read Access Token from local.properties
        val tmdbReadAccessToken = localProperties.getProperty("TMDB_READ_ACCESS_TOKEN", "")

        // Add the API key as a BuildConfig field
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        // Add the Read Access Token as a BuildConfig field
        buildConfigField("String", "TMDB_READ_ACCESS_TOKEN", "\"$tmdbReadAccessToken\"")

        // Optional: Expose as resValue if needed for XML
        resValue("string", "tmdb_api_key", "\"$tmdbApiKey\"")
        resValue("string", "tmdb_read_access_token", "\"$tmdbReadAccessToken\"")

    }
    buildFeatures {
        buildConfig = true // Add this line
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.register("generateDesktopSecretsFile") {
    description = "Generates a Kotlin file with API secrets for the desktop target."
    group = "build"

    val tmdbApiKeyProperty = project.providers.gradleProperty("TMDB_API_KEY_FROM_LOCAL")
        .orElse(localProperties.getProperty("TMDB_API_KEY", ""))
    val tmdbReadAccessTokenProperty = project.providers.gradleProperty("TMDB_READ_ACCESS_TOKEN_FROM_LOCAL")
        .orElse(localProperties.getProperty("TMDB_READ_ACCESS_TOKEN", ""))


    val firebaseAppIdProperty = project.providers.gradleProperty("FIREBASE_APP_ID_FROM_LOCAL")
        .orElse(localProperties.getProperty("FIREBASE_APP_ID", "")) // Reads from localProperties
    val firebaseApiKeyProperty = project.providers.gradleProperty("FIREBASE_API_KEY_FROM_LOCAL")
        .orElse(localProperties.getProperty("FIREBASE_API_KEY", "")) // Reads from localProperties
    val firebaseProjectIdProperty = project.providers.gradleProperty("FIREBASE_PROJECT_ID_FROM_LOCAL")
        .orElse(localProperties.getProperty("FIREBASE_PROJECT_ID", "")) // Reads from localProperties
    val firebaseStorageBucketProperty = project.providers.gradleProperty("FIREBASE_STORAGE_BUCKET_FROM_LOCAL")
        .orElse(localProperties.getProperty("FIREBASE_STORAGE_BUCKET", ""))
    val firebaseDatabaseUrlProperty = project.providers.gradleProperty("FIREBASE_DATABASE_URL_FROM_LOCAL")
        .orElse(localProperties.getProperty("FIREBASE_DATABASE_URL", ""))



    inputs.property("tmdbApiKey", tmdbApiKeyProperty)
    inputs.property("tmdbReadAccessToken", tmdbReadAccessTokenProperty)
    inputs.property("firebaseAppId", firebaseAppIdProperty)
    inputs.property("firebaseApiKey", firebaseApiKeyProperty)
    inputs.property("firebaseProjectId", firebaseProjectIdProperty)
    inputs.property("firebaseStorageBucket", firebaseStorageBucketProperty)
    inputs.property("firebaseDatabaseUrl", firebaseDatabaseUrlProperty)

    val outputDir = project.layout.buildDirectory.dir("generated/sources/secrets/desktop/main/com/ash/bingemaster/core/generated")
    val outputFileProvider = outputDir.map { dir -> dir.file("DesktopGeneratedSecrets.kt") }

    outputs.file(outputFileProvider).withPropertyName("outputSecretFile")

    doLast {
        val tmdbApiKey = tmdbApiKeyProperty.get()
        val tmdbReadAccessToken = tmdbReadAccessTokenProperty.get()
        val firebaseAppId = firebaseAppIdProperty.get()
        val firebaseApiKey = firebaseApiKeyProperty.get()
        val firebaseProjectId = firebaseProjectIdProperty.get()
        val firebaseStorageBucket = firebaseStorageBucketProperty.get()
        val firebaseDatabaseUrl = firebaseDatabaseUrlProperty.get()

        val outputFile = outputFileProvider.get().asFile

        outputFile.parentFile.mkdirs()
        outputFile.writeText(
            """
            package com.ash.bingemaster.core.generated
            
            internal const val GENERATED_TMDB_API_KEY: String = "$tmdbApiKey"
            internal const val GENERATED_TMDB_READ_ACCESS_TOKEN: String = "$tmdbReadAccessToken"
            internal const val GENERATED_FIREBASE_APP_ID: String = "$firebaseAppId"
            internal const val GENERATED_FIREBASE_API_KEY: String = "$firebaseApiKey"
            internal const val GENERATED_FIREBASE_PROJECT_ID: String = "$firebaseProjectId"
            internal const val GENERATED_FIREBASE_DATABASE_URL: String = "$firebaseDatabaseUrl"
            internal const val GENERATED_FIREBASE_STORAGE_BUCKET: String = "$firebaseStorageBucket"
            """.trimIndent()
        )
        println("Generated desktop secrets file at: ${outputFile.absolutePath}")
    }
}

tasks.named("desktopProcessResources") {
    dependsOn(tasks.named("generateDesktopSecretsFile"))
}

tasks.named("compileKotlinDesktop") {
    dependsOn(tasks.named("generateDesktopSecretsFile"))
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.ash.bingemaster.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.ash.bingemaster"
            packageVersion = "1.0.0"
        }
    }
}
