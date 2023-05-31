package com.mjob.moviecatalog.data.repository

import com.mjob.moviecatalog.data.datasource.CacheableDataSource
import com.mjob.moviecatalog.data.datasource.ReadOnlyDataSource
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.PlatformEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.PlatformResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Platform
import com.mjob.moviecatalog.data.repository.model.Show
import com.mjob.moviecatalog.data.repository.store.MovieKey
import com.mjob.moviecatalog.data.repository.store.PlatformKey
import com.mjob.moviecatalog.data.repository.store.ShowKeys
import com.mjob.moviecatalog.data.repository.store.converterForMovie
import com.mjob.moviecatalog.data.repository.store.converterForPlatform
import com.mjob.moviecatalog.data.repository.store.converterForShow
import com.mjob.moviecatalog.data.repository.store.toEpisode
import com.mjob.moviecatalog.data.repository.store.toMovie
import com.mjob.moviecatalog.data.repository.store.toMovieEntity
import com.mjob.moviecatalog.data.repository.store.toShow
import com.mjob.moviecatalog.data.repository.store.toShowEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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

    private val platformConverter = converterForPlatform()
   /* private val storePlatform =
        StoreBuilder.from<PlatformKey, List<PlatformResponse>, List<Platform>, List<PlatformEntity>>(
            fetcher = Fetcher.of<PlatformKey, List<PlatformResponse>> { _ ->
                remoteDataSource.getPlatforms()
            },
            sourceOfTruth = SourceOfTruth.of<PlatformKey, List<PlatformEntity>>(
                reader = { _ ->
                    localDataSource.getPlatforms()
                }, writer = { _: PlatformKey, platforms: List<PlatformEntity> ->
                    localDataSource.insertPlatforms(platforms)
                }
            )
        )
            .converter(platformConverter)
            .build()*/


    override fun getMovies(): Flow<Result<List<Movie>>> {
        return flow<Result<List<Movie>>> {
            storeMovie.stream(StoreReadRequest.cached(MovieKey, refresh = false))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            emit(Result.success(response.value))
                        }

                        is StoreReadResponse.Error -> {
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
                            emit(Result.success(response.value))
                        }

                        is StoreReadResponse.Error -> {
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

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map { favorites ->
            favorites.map { favorite ->
                favorite.toMovie()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteShows(): Flow<List<Show>> {
        return localDataSource.getFavoriteShows().map { favorites ->
            favorites.map { favorite ->
                favorite.toShow()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getPlatforms(): Flow<Result<List<Platform>>> {
        return flow<Result<List<Platform>>> {
            emit(Result.success(emptyList()))
            /*storePlatform.stream(StoreReadRequest.cached(PlatformKey, refresh = false))
                .collect { response ->
                    when (response) {
                        is StoreReadResponse.Data -> {
                            emit(Result.success(response.value))
                        }

                        is StoreReadResponse.Error -> {
                            emit(Result.failure(Exception(response.errorMessageOrNull())))
                        }

                        is StoreReadResponse.NoNewData -> {
                            emit(Result.success(emptyList()))
                        }

                        else -> {}
                    }
                }*/
        }
    }

    override fun setFavoriteShow(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return localDataSource.setFavoriteShow(id, isFavorite)
    }

    override fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return localDataSource.setFavoriteMovie(id, isFavorite)
    }

    override fun getMovie(id: Int): Flow<Movie?> {
        return localDataSource.getMovie(id)
            .map { movieEntity ->
                if (movieEntity != null && movieEntity.voteAverage == null && movieEntity.voteCount == null) {
                    val movieResponse = remoteDataSource.getMovie(id).firstOrNull()
                    val movieWithDetail = movieEntity.copy(
                        youtubeTrailer = movieResponse?.youtubeTrailer,
                        voteAverage = movieResponse?.voteAverage,
                        voteCount = movieResponse?.voteCount
                    ).toMovie()
                    updateMovie(movieWithDetail)
                    movieWithDetail
                } else {
                    movieEntity?.toMovie()
                }
            }.flowOn(Dispatchers.IO)
    }

    override fun getShow(id: Int): Flow<Show?> {
        return localDataSource.getShow(id)
            .map {
                if (it != null && it.voteAverage == null && it.voteCount == null) {
                    val showResponse = remoteDataSource.getShow(id).firstOrNull()
                    val showEntity = it.copy(
                        youtubeTrailer = showResponse?.youtubeTrailer,
                        voteAverage = showResponse?.voteAverage,
                        voteCount = showResponse?.voteCount
                    )
                    updateShow(showEntity.toShow())
                    showEntity
                } else {
                    it
                }
            }
            .map { showEntity ->
                val show = showEntity?.toShow()
                if (show?.episodes.orEmpty().isEmpty()) {
                    val episodes = remoteDataSource
                        .getEpisodes(id)
                        .data
                        .map { episodeResponse ->
                            episodeResponse.toEpisode()
                        }
                    val showWithEpisodes = show?.copy(episodes = episodes)
                    showWithEpisodes?.let { updateShow(it) }
                    showWithEpisodes
                } else {
                    show
                }
            }.flowOn(Dispatchers.IO)
    }

    private suspend fun updateMovie(movie: Movie) {
        localDataSource.updateMovie(movie.toMovieEntity())
    }

    private suspend fun updateShow(show: Show) {
        localDataSource.updateShow(show.toShowEntity())
    }
}