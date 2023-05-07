package com.mjob.moviecatalog.data.datasource.remote

import com.mjob.moviecatalog.BuildConfig
import com.mjob.moviecatalog.data.datasource.ReadOnlyDataSource
import com.mjob.moviecatalog.data.datasource.remote.model.EpisodeResponse
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val client: HttpClient) :
    ReadOnlyDataSource {
    override suspend fun getMovies(): List<MovieResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/movies?${params}").body()
    }

    override suspend fun getShows(): List<ShowResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/shows?${params}").body()
    }

    override suspend fun getEpisodes(showId: String): List<EpisodeResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/shows/$showId/episodes?${params}").body()
    }

    override suspend fun search(keyword: String): List<EpisodeResponse> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val params = "region=US&limit=100&sort=popularity"
    }
}