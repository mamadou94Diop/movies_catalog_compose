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
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val trailer: String? = null,
    val seasons: List<Season>? = null
) {
    val rating = if (voteAverage != null && voteCount != null) {
        String.format("%.2f", this.voteAverage) + "/10 (${voteCount} votes)"
    } else {
        "Not rated"
    }

    val release = if (isMovie()) releaseDate!! else firstAired!!

    fun isMovie() = releaseDate != null


}