package com.mjob.moviecatalog.domain.usecases

import com.mjob.moviecatalog.data.repository.MovieRepository
import com.mjob.moviecatalog.domain.model.Platform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(private val repository: MovieRepository) {
    fun execute(id: Int): Flow<List<Platform>> {
        return repository.getPlatforms()
            .map { result: Result<List<com.mjob.moviecatalog.data.repository.model.Platform>> ->
                if (result.isSuccess) {
                    result.getOrNull().orEmpty()
                        .map { platform ->
                            Platform(
                                displayName = platform.displayName,
                                source = platform.source,
                                type = platform.type
                            )

                        }
                } else {
                    emptyList()
                }

            }
            .flowOn(Dispatchers.IO)
    }
}