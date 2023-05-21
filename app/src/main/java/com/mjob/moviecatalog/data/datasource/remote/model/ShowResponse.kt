package com.mjob.moviecatalog.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class ShowResponse(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("first_aired")
    val firstAired: String,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Double? = null,
    @SerializedName("youtube_trailer")
    val youtubeTrailer: String? = null
)