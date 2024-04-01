package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.data.repository.MovieRepository
import javax.inject.Inject

class GetContentTrailersUseCase @Inject constructor(private val repository: MovieRepository) {
    fun execute() = repository.getTrailers()
}