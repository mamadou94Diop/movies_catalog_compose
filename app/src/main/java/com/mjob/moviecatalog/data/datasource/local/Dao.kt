package com.mjob.moviecatalog.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity

@Dao
interface Dao {
    @Query("SELECT * FROM movie where ANY () AND ")
    fun getGetMovies(genres: List<String>, platforms: List<String>): List<MovieEntity>

    @Update
    fun updateShow(show: ShowEntity)

    @Update
    fun updateMovie(show: MovieEntity)
}