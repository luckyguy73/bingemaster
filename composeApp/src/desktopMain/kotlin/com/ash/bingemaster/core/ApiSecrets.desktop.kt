package com.ash.bingemaster.core

import com.ash.bingemaster.core.generated.GENERATED_TMDB_API_KEY
import com.ash.bingemaster.core.generated.GENERATED_TMDB_READ_ACCESS_TOKEN

actual object ApiSecrets {
    actual val tmdbApiKey: String = GENERATED_TMDB_API_KEY
    actual val tmdbReadAccessToken: String = GENERATED_TMDB_READ_ACCESS_TOKEN
}
