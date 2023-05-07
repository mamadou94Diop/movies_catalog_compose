package com.mjob.moviecatalog.data.repository.model


data class Show(
    val backdropPath: String,
    val genres: List<String>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val firstAired: String,
    val isFavorite: Boolean = false,
    val episodes: List<Episode> = emptyList()
)