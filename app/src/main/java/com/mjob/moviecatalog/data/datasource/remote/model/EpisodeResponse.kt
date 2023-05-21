package com.mjob.moviecatalog.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("data")
    val data: List<EpisodeDataResponse>
)

data class EpisodeDataResponse(
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
    @SerializedName("sources")
    val sources: List<Any>,
    @SerializedName("thumbnail_path")
    val thumbnailPath: String,
    @SerializedName("title")
    val title: String
)