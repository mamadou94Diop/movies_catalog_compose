package com.mjob.moviecatalog.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mjob.moviecatalog.data.datasource.local.model.EpisodeEntity
import com.mjob.moviecatalog.data.datasource.local.typeconverter.EpisodeEntityConverter
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity

@TypeConverters(EpisodeEntityConverter::class)
@Database(
    version = 4,
    entities = [MovieEntity::class, ShowEntity::class, EpisodeEntity::class],
)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}