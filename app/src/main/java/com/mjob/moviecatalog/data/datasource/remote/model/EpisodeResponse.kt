package com.mjob.moviecatalog.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("episode_number")
    val episodeNumber: Int,
    @SerializedName("first_aired")
    val firstAired: String,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("season_number")
    val seasonNumber: Int,
    @SerializedName("show_id")
    val showId: Int,
    @SerializedName("thumbnail_path")
    val thumbnailPath: String,
    @SerializedName("title")
    val title: String
)