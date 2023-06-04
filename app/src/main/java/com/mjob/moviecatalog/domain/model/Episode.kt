package com.mjob.moviecatalog.domain.model

data class Episode(
    val episodeNumber: Int,
    val firstAired: String?,
    val id: Int,
    val seasonNumber: Int,
    val showId: Int,
    val thumbnailPath: String?,
    val title: String
)