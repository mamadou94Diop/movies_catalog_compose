package com.mjob.moviecatalog.data.datasource

import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.datasource.remote.model.EpisodeResponse
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import kotlinx.coroutines.flow.Flow

interface ReadOnlyDataSource {
    suspend fun getMovies(): List<MovieResponse>
    suspend fun getShows(): List<ShowResponse>
    suspend fun getEpisodes(showId: String): List<EpisodeResponse>
    suspend fun search(keyword: String): List<EpisodeResponse>
}

interface CacheableDataSource {
    fun getMovies(): Flow<List<MovieEntity>>
    suspend fun insertMovies(movies: List<MovieEntity>)
    suspend fun deleteMovies()
    fun getShows(): Flow<List<ShowEntity>>
    suspend fun insertShows(shows: List<ShowEntity>)
    suspend fun deleteShows()

    fun getFavoriteMovies(): Flow<List<MovieEntity>>
    fun getFavoriteShows(): Flow<List<ShowEntity>>
    fun setFavoriteShow(id: Int, isFavorite: Boolean): Flow<Boolean>
    fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean>
}