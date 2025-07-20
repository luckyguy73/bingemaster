// desktopMain/kotlin/com/ash/bingemaster/core/ApiSecrets.desktop.kt
package com.ash.bingemaster.core

actual object ApiSecrets {
    actual val tmdbApiKey: String = System.getenv("TMDB_API_KEY")
        ?: error("TMDB_API_KEY environment variable not set.")
    actual val tmdbReadAccessToken: String = System.getenv("TMDB_READ_ACCESS_TOKEN")
        ?: error("TMDB_READ_ACCESS_TOKEN environment variable not set.")
}
