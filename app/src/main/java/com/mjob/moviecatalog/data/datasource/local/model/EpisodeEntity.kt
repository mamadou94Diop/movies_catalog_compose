package com.mjob.moviecatalog.data.datasource.local.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episode")
data class EpisodeEntity(
    @ColumnInfo("episode_number")
    val episodeNumber: Int,
    @ColumnInfo("first_aired")
    val firstAired: String?,
    @ColumnInfo("_id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo("season_number")
    val seasonNumber: Int,
    @ColumnInfo("show_id")
    val showId: Int,
    @ColumnInfo("thumbnail_path")
    val thumbnailPath: String?,
    @ColumnInfo("title")
    val title: String
)