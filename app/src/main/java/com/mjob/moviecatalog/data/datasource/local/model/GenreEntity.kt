package com.mjob.moviecatalog.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "genre")
data class GenreEntity(@ColumnInfo("name") val name: String)