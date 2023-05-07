package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.BuildConfig
import com.mjob.moviecatalog.data.repository.MovieRepository
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Show
import com.mjob.moviecatalog.domain.model.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetContentUseCase @Inject constructor(private val repository: MovieRepository) {
    fun execute(): Flow<Result<List<Content>>> {
        return flow {
            repository.getMovies()
                .zip(
                    repository.getShows()
                ) { moviesResult: Result<List<Movie>>, showsResult: Result<List<Show>> ->
                    moviesResult to showsResult
                }
                .collect { (movies, shows) ->
                    when {
                        movies.isFailure && shows.isFailure -> emit(
                            Result.failure(
                                Exception(movies.exceptionOrNull())
                            )
                        )

                        else -> {
                            val moviesContent = movies.getOrNull().orEmpty()
                                .flatMap { movie ->
                                    movie.genres.take(1).map { genre ->
                                        Content(
                                            id = movie.id,
                                            title = movie.originalTitle,
                                            overview = movie.overview,
                                            genre = genre,
                                            backdropPath = "${BuildConfig.PICTURE_BASE_URL}/${movie.backdropPath}",
                                            posterPath = "${BuildConfig.PICTURE_BASE_URL}/${movie.posterPath}",
                                            releaseDate = movie.releaseDate,
                                            isFavorite = movie.isFavorite
                                        )
                                    }

                                }
                            val showsContent = shows.getOrNull().orEmpty()
                                .flatMap { show ->
                                    show.genres.take(1).map { genre ->
                                        Content(
                                            id = show.id,
                                            title = show.originalTitle,
                                            overview = show.overview,
                                            genre = genre,
                                            backdropPath = "${BuildConfig.PICTURE_BASE_URL}/${show.backdropPath}",
                                            posterPath = "${BuildConfig.PICTURE_BASE_URL}/${show.posterPath}",
                                            firstAired = show.firstAired,
                                            isFavorite = show.isFavorite
                                        )
                                    }

                                }
                            emit(Result.success(moviesContent.plus(showsContent)))
                        }
                    }
                }
        }
    }
}