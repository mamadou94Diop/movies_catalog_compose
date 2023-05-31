package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.data.repository.MovieRepository
import com.mjob.moviecatalog.domain.mapper.toContent
import com.mjob.moviecatalog.domain.model.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetFavoriteContentsUseCase @Inject constructor(private val repository: MovieRepository) {
    fun execute(): Flow<List<Content>> {
        return repository.getFavoriteMovies()
            .zip(
                repository.getFavoriteShows()
            ) { movies, shows ->
                val favoritesMovies = movies.flatMap { movie ->
                    movie.genres.take(1).map { genre ->
                        movie.toContent(genre)
                    }
                }

                val favoritesShows = shows.flatMap { show ->
                    show.genres.take(1).map { genre ->
                        show.toContent(genre)
                    }
                }
                favoritesMovies.plus(favoritesShows)
            }
    }
}