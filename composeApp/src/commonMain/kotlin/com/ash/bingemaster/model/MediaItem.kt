package com.ash.bingemaster.model

import kotlinx.serialization.Serializable

/**
 * Common interface for all media items (e.g., TV Series, Movies) that can be tracked.
 * These properties are generally fetched from an external API like TMDB and represent
 * the canonical details of the media, not user-specific tracking information.
 */
@Serializable
interface MediaItem {
    /** Unique ID (e.g., from TMDB, like "tv/60735" or "movie/299536"). */
    val id: String

    /** The main title of the media item. */
    val title: String

    /** A synopsis or description of the media item. Null if not available. */
    val overview: String?

    /** Partial path to the poster image (e.g., "/ ZustjHn1gGmyhI6x53o2M59X7EW.jpg"). Null if not available. */
    val posterPath: String?

    /** Partial path to the backdrop image. Null if not available. */
    val backdropPath: String?

    /** Release date (e.g., "2019-04-24" for movies, "first_air_date" for TV). Null if not available. */
    val releaseDate: String?

    /** Average vote or rating (e.g., TMDB rating, typically 0-10). Null if not available. */
    val voteAverage: Double?

    /** List of genre names associated with the media item. */
    val genres: List<String>
}
