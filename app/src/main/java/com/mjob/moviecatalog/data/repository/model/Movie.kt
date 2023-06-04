package com.mjob.moviecatalog.data.repository.model

data class Movie(
    val backdropPath: String,
    val genres: List<String>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double? = null,
    val voteCount: Double? = null,
    val youtubeTrailer: String? = null,
    val isFavorite: Boolean = false,
    val platforms: List<String> = emptyList(),
)