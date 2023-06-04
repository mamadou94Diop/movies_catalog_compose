package com.mjob.moviecatalog.data.datasource.remote.model
import com.google.gson.annotations.SerializedName


data class SourceResponse(
    @SerializedName("source")
    val source: String
)