package com.ash.bingemaster.model

import kotlinx.serialization.Serializable

/**
 * Represents the detailed information for a TV Series, typically fetched from an external API.
 * This class describes the canonical details of the series itself.
 */
@Serializable
data class TvSeries(
    /** Unique ID from the data source (e.g., TMDB ID). */
    override val id: String,

    /** The main title of the TV series. */
    override val title: String,

    /** A synopsis or description of the TV series. Null if not available. */
    override val overview: String?,

    /** Partial path to the poster image. Null if not available. */
    override val posterPath: String?,

    /** Partial path to the backdrop image. Null if not available. */
    override val backdropPath: String?,

    /** The first air date of the TV series. Null if not available. */
    override val releaseDate: String?,

    /** Average vote or rating from the data source. Null if not available. */
    override val voteAverage: Double?,

    /** List of genre names associated with the TV series. */
    override val genres: List<String> = emptyList(),

    /** The total number of seasons. Null if not available. */
    val numberOfSeasons: Int?,

    /** The total number of episodes across all seasons. Null if not available. */
    val numberOfEpisodes: Int?,

    /** List of networks the TV series aired on. */
    val networks: List<Network> = emptyList(),

    /** The current status of the TV series (e.g., "Returning Series", "Ended", "Canceled"). Null if not available. */
    val status: String?,

    /** The air date of the last episode that aired. Null if not available. */
    val lastAirDate: String?,

    /** Details of the next episode scheduled to air. Null if no upcoming episode information is available. */
    val nextEpisodeToAir: Episode? = null,

    /** Overview information for each season of the TV series. */
    val seasons: List<SeasonOverview> = emptyList()
) : MediaItem

/**
 * Represents a television network.
 */
@Serializable
data class Network(
    /** Unique ID of the network. */
    val id: Int,

    /** Name of the network. */
    val name: String,

    /** Partial path to the network's logo image. Null if not available. */
    val logoPath: String?,

    /** Origin country of the network. Null if not available. */
    val originCountry: String?
)

/**
 * Represents details of a single TV episode.
 */
@Serializable
data class Episode(
    /** Unique ID of the episode. */
    val id: Int,

    /** Name or title of the episode. Null if not available. */
    val name: String?,

    /** A synopsis or description of the episode. Null if not available. */
    val overview: String?,

    /** The air date of the episode. Null if not available. */
    val airDate: String?,

    /** The episode number within its season. Null if not available. */
    val episodeNumber: Int?,

    /** The season number this episode belongs to. Null if not available. */
    val seasonNumber: Int?,

    /** Partial path to a still image from the episode. Null if not available. */
    val stillPath: String?,

    /** Average vote or rating for the episode. Null if not available. */
    val voteAverage: Double?
)

/**
 * Represents an overview of a single TV season.
 */
@Serializable
data class SeasonOverview(
    /** Unique ID of the season. */
    val id: Int,

    /** The air date of the season's first episode. Null if not available. */
    val airDate: String?,

    /** The total number of episodes in this season. Null if not available. */
    val episodeCount: Int?,

    /** Name or title of the season. Null if not available. */
    val name: String?,

    /** A synopsis or description of the season. Null if not available. */
    val overview: String?,

    /** Partial path to the poster image for this season. Null if not available. */
    val posterPath: String?,

    /** The season number. Null if not available. */
    val seasonNumber: Int?
)
