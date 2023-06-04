package com.mjob.moviecatalog.data.repository.model

data class Show(
    val backdropPath: String,
    val genres: List<String>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val firstAired: String,
    val voteAverage: Double? = null,
    val voteCount: Double? = null,
    val youtubeTrailer: String? = null,
    val isFavorite: Boolean = false,
    val episodes: List<Episode> = emptyList(),
    val platforms: List<String> = emptyList(),
)