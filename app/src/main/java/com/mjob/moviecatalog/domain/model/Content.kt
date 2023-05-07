package com.mjob.moviecatalog.domain.model

data class Content(
    val id: Int,
    val title: String,
    val overview: String,
    val genre: String,
    val backdropPath: String,
    val posterPath: String,
    val isFavorite: Boolean = false,
    val releaseDate: String? = null,
    val firstAired: String? = null,
    val seasons: List<Episode>? = null
){
    fun isMovie() = releaseDate != null
}