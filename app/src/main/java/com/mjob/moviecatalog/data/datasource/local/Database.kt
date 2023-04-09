package com.mjob.moviecatalog.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mjob.moviecatalog.data.datasource.local.model.EpisodeEntity
import com.mjob.moviecatalog.data.datasource.local.model.GenreEntity
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.PlatformEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity

@Database(
    version = 1,
    entities = [GenreEntity::class, MovieEntity::class, PlatformEntity::class, ShowEntity::class, EpisodeEntity::class]
)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}