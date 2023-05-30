package com.mjob.moviecatalog.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platform")
data class PlatformEntity(
    @ColumnInfo("display_name")
    val displayName: String,
    @ColumnInfo("source")
    @PrimaryKey
    val source: String,
    @ColumnInfo("type")
    val type: String
)