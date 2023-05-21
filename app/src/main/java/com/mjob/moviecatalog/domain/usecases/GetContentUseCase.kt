package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.data.repository.MovieRepository
import com.mjob.moviecatalog.domain.mapper.toContent
import com.mjob.moviecatalog.domain.model.Content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetContentUseCase @Inject constructor(private val repository: MovieRepository) {
    @OptIn(FlowPreview::class)
    fun execute(id: Int): Flow<Content> {
        return repository.getMovie(id)
            .map { movie ->
                movie?.toContent(movie.genres.joinToString(","))
            }
            .flatMapConcat { movieContent ->
                if (movieContent != null) {
                    flowOf(movieContent)
                } else {
                    repository.getShow(id)
                        .filterNotNull()
                        .map {
                            it.toContent(it.genres.joinToString(","))
                        }
                }
            }
            .flowOn(Dispatchers.IO)
    }
}