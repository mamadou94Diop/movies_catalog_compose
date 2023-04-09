package com.mjob.moviecatalog.data.datasource.remote

import com.mjob.moviecatalog.data.datasource.DataSource
import com.mjob.moviecatalog.data.datasource.remote.model.EpisodeEntity
import com.mjob.moviecatalog.data.datasource.remote.model.GenreEntity
import com.mjob.moviecatalog.data.datasource.remote.model.PlatformEntity
import com.mjob.moviecatalog.data.datasource.remote.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.remote.model.ShowEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val client: HttpClient) :
    DataSource {
    override suspend fun getGenres(): List<GenreEntity> {
        return client.get("/genres").body()
    }

    override suspend fun getPlatform(country: String): List<PlatformEntity> {
        return client.get("/sources").body()
    }

    override suspend fun getMovies(country: String, platforms: String): List<MovieEntity> {
        val params = "region=$country&sources=$platforms&limit=100&sort=popularity"
        return client.get("/movies?$params").body()
    }

    override suspend fun getShows(country: String, platforms: String): List<ShowEntity> {
        val params = "region=$country&sources=$platforms&limit=100&sort=popularity"
        return client.get("/shows?$params").body()
    }

    override suspend fun getEpisodes(showId: String, country: String): List<EpisodeEntity> {
        val params = "region=$country&limit=100&sort=popularity"
        return client.get("/shows/$showId/episodes?$params").body()
    }


}