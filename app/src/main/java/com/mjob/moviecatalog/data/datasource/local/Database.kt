package com.mjob.moviecatalog.data.datasource.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mjob.moviecatalog.data.datasource.local.model.EpisodeEntity
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.datasource.local.typeconverter.EpisodeEntityConverter
import com.mjob.moviecatalog.data.datasource.local.typeconverter.PlatformConverter

@Database(
    version = 1,
    exportSchema = true,
    entities = [MovieEntity::class, ShowEntity::class, EpisodeEntity::class],
)
@TypeConverters(PlatformConverter::class, EpisodeEntityConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}