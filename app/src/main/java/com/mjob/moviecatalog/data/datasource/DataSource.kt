package com.mjob.moviecatalog.data.datasource

import com.mjob.moviecatalog.data.datasource.remote.model.EpisodeResponse
import com.mjob.moviecatalog.data.datasource.remote.model.GenreResponse
import com.mjob.moviecatalog.data.datasource.remote.model.PlatformResponse
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse

interface DataSource {
    suspend fun getGenres(): List<GenreResponse>
    suspend fun getPlatform(country: String): List<PlatformResponse>
    suspend fun getMovies(country: String, platforms: String): List<MovieResponse>
    suspend fun getShows(country: String, platforms: String): List<ShowResponse>
    suspend fun getEpisodes(showId: String, country: String): List<EpisodeResponse>
}

interface CacheableMovieDataSource {
    fun getFavoriteMovies()
    fun addMovieToFavorites()
    fun removeMovieFromFavorites(id: String)
}