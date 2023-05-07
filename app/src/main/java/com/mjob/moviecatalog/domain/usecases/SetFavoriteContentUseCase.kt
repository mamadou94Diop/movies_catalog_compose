package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetFavoriteContentUseCase @Inject constructor(private val repository: MovieRepository) {
    fun execute(id: Int, isFavorite: Boolean, isMovie: Boolean): Flow<Boolean> {
        return if (isMovie) {
            setFavoriteMovie(id, isFavorite)
        } else {
            setFavoriteShow(id, isFavorite)
        }
    }

    private fun setFavoriteShow(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return repository.setFavoriteShow(id, isFavorite)
    }

    private fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return repository.setFavoriteMovie(id, isFavorite)
    }
}