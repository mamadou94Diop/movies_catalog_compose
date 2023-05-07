package com.mjob.moviecatalog.data.datasource.remote.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
)