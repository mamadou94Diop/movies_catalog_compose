package com.mjob.moviecatalog.data.datasource.remote.model
import com.google.gson.annotations.SerializedName


data class PlatformResponse(
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("type")
    val type: String
)