package com.mjob.moviecatalog.data.datasource.local

import com.mjob.moviecatalog.data.datasource.CacheableDataSource
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.repository.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.concatWith
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: Dao) : CacheableDataSource {

    override fun getMovies(): Flow<List<MovieEntity>> {
        return dao.getMovies()
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        try {
            dao.insertMovies(movies)
        } catch (exception: Exception) {
            println(" ${exception.stackTraceToString()}")
        }
    }

    override suspend fun deleteMovies() {
        dao.deleteMovies()
    }

    override fun getShows(): Flow<List<ShowEntity>> {
        return dao.getShows()
    }

    override suspend fun insertShows(shows: List<ShowEntity>) {
        try {
            dao.insertShows(shows)
        } catch (exception: Exception) {
            println(" ${exception.stackTraceToString()}")
        }
    }

    override suspend fun deleteShows() {
        dao.deleteShows()
    }

    override fun getFavoriteMovies(): Flow<List<MovieEntity>> {
        return dao.getFavoriteMovies()
    }

    override fun getFavoriteShows(): Flow<List<ShowEntity>> {
        return dao.getFavoriteShows()
    }

    override fun setFavoriteShow(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return flow {
            dao.getShow(id)
               .take(1)
                .collect {
                    if (it == null) {
                        emit(false)
                    } else {
                        val affectedRow = dao.updateShow(it.copy(isFavorite = isFavorite))
                        emit(affectedRow == 1)
                    }
                }
        }
    }

    override fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return flow {
            dao.getMovie(id)
                .take(1)
                .collect {
                    if (it == null) {
                        emit(false)
                    } else {
                        val affectedRow = dao.updateMovie(it.copy(isFavorite = isFavorite))
                        emit(affectedRow == 1)
                    }
                }
        }
    }
}