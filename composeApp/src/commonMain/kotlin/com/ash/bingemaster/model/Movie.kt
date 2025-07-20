package com.ash.bingemaster.model

import kotlinx.serialization.Serializable

/**
 * Represents the detailed information for a Movie, typically fetched from an external API.
 * This class describes the canonical details of the movie itself.
 */
@Serializable
data class Movie(
    /** Unique ID from the data source (e.g., TMDB ID). */
    override val id: String,

    /** The main title of the movie. */
    override val title: String,

    /** A synopsis or description of the movie. Null if not available. */
    override val overview: String?,

    /** Partial path to the poster image. Null if not available. */
    override val posterPath: String?,

    /** Partial path to the backdrop image. Null if not available. */
    override val backdropPath: String?,

    /** The release date of the movie. Null if not available. */
    override val releaseDate: String?,

    /** Average vote or rating from the data source. Null if not available. */
    override val voteAverage: Double?,

    /** List of genre names associated with the movie. */
    override val genres: List<String> = emptyList(),

    /** Movie runtime in minutes. Null if not available. */
    val runtime: Int?,

    /** The tagline or catchphrase of the movie. Null if not available. */
    val tagline: String?,

    /** Information on where to watch the movie, typically grouped by region. Null if not available. */
    val watchProviders: WatchProviders? = null

    // We can add director and mainCast here later if needed,
    // they would likely be List<CrewMember> and List<CastMember> respectively.
) : MediaItem

/**
 * Represents information about a specific watch provider (e.g., Netflix, Hulu).
 */
@Serializable
data class WatchProviderInfo(
    /** Unique ID of the provider. */
    val providerId: Int,

    /** Name of the provider. */
    val providerName: String,

    /** Partial path to the provider's logo image. Null if not available. */
    val logoPath: String?
)

/**
 * Represents watch provider availability within a specific region (e.g., country).
 */
@Serializable
data class WatchProviderRegion(
    /** Direct link to the movie on the streaming service or store for this region. Null if not available. */
    val link: String?,

    /** List of flatrate (subscription) providers. Null if not available or empty. */
    val flatrate: List<WatchProviderInfo>? = null,

    /** List of providers where the movie can be rented. Null if not available or empty. */
    val rent: List<WatchProviderInfo>? = null,

    /** List of providers where the movie can be purchased. Null if not available or empty. */
    val buy: List<WatchProviderInfo>? = null
)

/**
 * Container for watch provider information, typically keyed by region codes (e.g., "US", "GB").
 */
@Serializable
data class WatchProviders(
    /**
     * A map where the key is the country code (e.g., "US") and the value contains
     * the watch provider details for that region. Null if no provider information is available.
     */
    val results: Map<String, WatchProviderRegion>? = null
)
