package com.mjob.moviecatalog.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM show")
    fun getShows(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM movie where _id =:id")
    fun getMovie(id: Int): Flow<MovieEntity?>

    @Query("SELECT * FROM show where _id =:id")
    fun getShow(id: Int): Flow<ShowEntity?>

    @Query("SELECT * FROM movie where is_favorite=1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM show where is_favorite=1")
    fun getFavoriteShows(): Flow<List<ShowEntity>>

    @Update(entity = ShowEntity::class)
    suspend fun updateShow(show: ShowEntity): Int

    @Update(entity = MovieEntity::class)
    suspend fun updateMovie(movie: MovieEntity): Int

    @Insert(entity = ShowEntity::class)
    fun insertShows(shows: List<ShowEntity>)

    @Insert(entity = MovieEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE from movie")
    fun deleteMovies()

    @Query("DELETE from show")
    fun deleteShows()
}