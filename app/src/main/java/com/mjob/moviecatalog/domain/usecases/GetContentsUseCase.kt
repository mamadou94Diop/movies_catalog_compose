package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.data.repository.MovieRepository
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Show
import com.mjob.moviecatalog.domain.mapper.toContent
import com.mjob.moviecatalog.domain.model.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetContentsUseCase @Inject constructor(private val repository: MovieRepository) {
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
                                        movie.toContent(genre)
                                    }

                                }
                            val showsContent = shows.getOrNull().orEmpty()
                                .flatMap { show ->
                                    show.genres.take(1).map { genre ->
                                        show.toContent(genre)
                                    }

                                }
                            emit(Result.success(moviesContent.plus(showsContent)))
                        }
                    }
                }
        }
    }
}