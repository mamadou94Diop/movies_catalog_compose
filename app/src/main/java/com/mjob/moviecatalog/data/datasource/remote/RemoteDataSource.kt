package com.mjob.moviecatalog.data.datasource.remote

import com.mjob.moviecatalog.BuildConfig
import com.mjob.moviecatalog.data.datasource.ReadOnlyDataSource
import com.mjob.moviecatalog.data.datasource.remote.model.EpisodeResponse
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val client: HttpClient) :
    ReadOnlyDataSource {
    override suspend fun getMovies(): List<MovieResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/movies?${params}").body()
    }

    override suspend fun getShows(): List<ShowResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/shows?${params}").body()
    }

    override suspend fun getShow(id: Int): List<ShowResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/shows/$id/?platform=android&limit=100").body()
    }

    override suspend fun getMovie(id: Int): List<MovieResponse> {
        return client.get("${BuildConfig.API_BASE_URL}/movies/$id/?&platform=android").body()
    }

    override suspend fun getEpisodes(showId: Int): EpisodeResponse{
        return client.get("${BuildConfig.API_BASE_URL}/shows/$showId/episodes?sort=regular&platform=android")
            .body()
    }

    companion object {
        private const val params = "region=US&limit=100&sort=popularity"
    }
}