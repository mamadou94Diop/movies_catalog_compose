package com.mjob.moviecatalog.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @ColumnInfo("backdrop_path")
    val backdropPath: String,
    @ColumnInfo("genres")
    val genres: String,
    @ColumnInfo("_id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo("original_title")
    val originalTitle: String,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("poster_path")
    val posterPath: String,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean = false
)