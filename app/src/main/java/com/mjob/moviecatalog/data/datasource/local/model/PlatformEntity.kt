package com.mjob.moviecatalog.data.datasource.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platfrom")
data class PlatformEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo("display_name") val displayName: String,
    @ColumnInfo("info") val info: String,
    @ColumnInfo("source") val source: String,
    @ColumnInfo("type") val type: String
)