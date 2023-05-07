package com.mjob.moviecatalog.data.repository.model

data class Movie(
    val backdropPath: String,
    val genres: List<String>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val isFavorite: Boolean = false
)