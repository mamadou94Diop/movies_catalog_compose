package com.mjob.moviecatalog.data.repository

import com.mjob.moviecatalog.data.datasource.CacheableDataSource
import com.mjob.moviecatalog.data.datasource.ReadOnlyDataSource
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.store.MovieKey
import com.mjob.moviecatalog.data.repository.store.converterForMovie
import com.mjob.moviecatalog.data.repository.model.Show
import com.mjob.moviecatalog.data.repository.store.ShowKeys
import com.mjob.moviecatalog.data.repository.store.converterForShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: CacheableDataSource,
    private val remoteDataSource: ReadOnlyDataSource,
) : MovieRepository {

    private val movieConverter = converterForMovie()
    private val storeMovie =
        StoreBuilder.from<MovieKey, List<MovieResponse>, List<Movie>, List<MovieEntity>>(
            fetcher = Fetcher.of<MovieKey, List<MovieResponse>> { _ ->
                remoteDataSource.getMovies()
            },
            sourceOfTruth = SourceOfTruth.of<MovieKey, List<MovieEntity>>(
                reader = { _ ->
                    localDataSource.getMovies()
                }, writer = { _: MovieKey, movies: List<MovieEntity> ->
                    localDataSource.insertMovies(movies)
                }, deleteAll = {
                    localDataSource.deleteMovies()
                }
            )
        )
            .converter(movieConverter)
            .build()


    private val showConverter = converterForShow()
    private val storeShow =
        StoreBuilder.from<ShowKeys, List<ShowResponse>, List<Show>, List<ShowEntity>>(
            fetcher = Fetcher.of<ShowKeys, List<ShowResponse>> { _ ->
                remoteDataSource.getShows()
            },
            sourceOfTruth = SourceOfTruth.of<ShowKeys, List<ShowEntity>>(
                reader = { _ ->
                    localDataSource.getShows()
                }, writer = { _: ShowKeys, shows: List<ShowEntity> ->
                    localDataSource.insertShows(shows)
                }, deleteAll = {
                    localDataSource.deleteShows()
                }
            )
        )
            .converter(showConverter)
            .build()


    override fun getMovies(): Flow<Result<List<Movie>>> {
        return flow<Result<List<Movie>>> {
            storeMovie.stream(StoreReadRequest.cached(MovieKey, refresh = false))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            println("  origin movies = ${response.origin.name}")
                            println("  origin movies = ${response.dataOrNull()}")
                            emit(Result.success(response.value))
                        }

                        is StoreReadResponse.Error -> {
                            println("  error movies = ${response.errorMessageOrNull()}")
                            println("  error movies = ${response.origin.name}")

                            emit(Result.failure(Exception(response.errorMessageOrNull())))
                        }

                        is StoreReadResponse.NoNewData -> {
                            emit(Result.success(emptyList()))
                        }

                        else -> {}
                    }

                }
        }
    }

    override fun getShows(): Flow<Result<List<Show>>> {
        return flow<Result<List<Show>>> {
            storeShow.stream(StoreReadRequest.cached(ShowKeys.Read.All, refresh = false))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            println("  origin shows = ${response.origin.name}")
                            println("  data shows = ${response.dataOrNull()}")
                            emit(Result.success(response.value))
                        }

                        is StoreReadResponse.Error -> {
                            println("  error shows = ${response.errorMessageOrNull()}")
                            emit(Result.failure(Exception(response.errorMessageOrNull())))
                        }

                        is StoreReadResponse.NoNewData -> {
                            emit(Result.success(emptyList()))
                        }

                        else -> {}
                    }
                }
        }
    }

    override fun setFavoriteShow(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return localDataSource.setFavoriteShow(id, isFavorite)
    }

    override fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return localDataSource.setFavoriteMovie(id, isFavorite)
    }
}