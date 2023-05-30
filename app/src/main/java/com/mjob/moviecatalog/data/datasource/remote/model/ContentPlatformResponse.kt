package com.mjob.moviecatalog.data.datasource.remote.model
import com.google.gson.annotations.SerializedName


data class ContentPlatformResponse(
    @SerializedName("source")
    val source: String
)