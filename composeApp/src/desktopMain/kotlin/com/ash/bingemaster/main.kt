package com.ash.bingemaster

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ash.bingemaster.core.generated.GENERATED_FIREBASE_API_KEY
import com.ash.bingemaster.core.generated.GENERATED_FIREBASE_APP_ID
import com.ash.bingemaster.core.generated.GENERATED_FIREBASE_DATABASE_URL
import com.ash.bingemaster.core.generated.GENERATED_FIREBASE_PROJECT_ID
import com.ash.bingemaster.core.generated.GENERATED_FIREBASE_STORAGE_BUCKET
import com.ash.bingemaster.di.initializeKoin
import com.google.firebase.FirebasePlatform
import dev.gitlive.firebase.FirebaseOptions
import org.koin.core.context.stopKoin
import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

/**
 * Main entry point for the BingeMaster desktop application.
 * Initializes Koin for dependency injection and Firebase services, then launches the UI.
 */
fun main() = application {
    initializeKoin {
    }

    try {
        FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {
            private val storage = mutableMapOf<String, String>()

            override fun store(key: String, value: String) {
                storage[key] = value
            }

            override fun retrieve(key: String): String? = storage[key]

            override fun clear(key: String) {
                storage.remove(key)
            }

            override fun log(msg: String) {
                println("FirebasePlatform Log: $msg")
            }
        })
        println("Basic com.google.firebase.FirebasePlatform initialized (for store/retrieve/log).")
    } catch (e: Exception) {
        println("Error initializing basic com.google.firebase.FirebasePlatform: ${e.message}")
        e.printStackTrace()
    }

    val firebaseOptions = FirebaseOptions(
        applicationId = GENERATED_FIREBASE_APP_ID, // Corresponds to setApplicationId
        apiKey = GENERATED_FIREBASE_API_KEY,           // Corresponds to setApiKey
        projectId = GENERATED_FIREBASE_PROJECT_ID,     // Corresponds to setProjectId
        databaseUrl = GENERATED_FIREBASE_DATABASE_URL.ifBlank { null }, // Corresponds to setDatabaseURL
        storageBucket = GENERATED_FIREBASE_STORAGE_BUCKET.ifBlank { null } // Corresponds to setStorageBucket
    )

    Firebase.initialize(Application(), firebaseOptions)

    Window(
        onCloseRequest = {
            exitApplication()
            stopKoin()
        },
        title = "BingeMaster",
    ) {
        App()
    }
}
