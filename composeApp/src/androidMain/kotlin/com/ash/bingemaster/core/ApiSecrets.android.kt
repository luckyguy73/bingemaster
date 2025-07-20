package com.ash.bingemaster.core

import com.ash.bingemaster.BuildConfig // Your generated BuildConfig

actual object ApiSecrets {
    actual val tmdbApiKey: String = BuildConfig.TMDB_API_KEY
    actual val tmdbReadAccessToken: String = BuildConfig.TMDB_READ_ACCESS_TOKEN
}
