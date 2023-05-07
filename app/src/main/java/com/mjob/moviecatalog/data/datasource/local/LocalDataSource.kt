package com.mjob.moviecatalog.data.datasource.local

import com.mjob.moviecatalog.data.datasource.CacheableDataSource
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.repository.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
            println("diop ${exception.stackTraceToString()}")
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
            println("diop ${exception.stackTraceToString()}")
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
            val show = dao.getShow(id)
            if (show == null) {
                emit(false)
            } else {
                val updatedShow = show.copy(isFavorite = isFavorite)
                val affectedRow = dao.updateShow(updatedShow)
                if (affectedRow == 1) {
                    emit(true)
                } else {
                    emit(false)
                }
            }
        }
    }

    override fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean> {
        return flow {
            var movie: MovieEntity?
            withContext(Dispatchers.IO){
                movie = dao.getMovie(id)
            }

            if (movie == null) {
                emit(false)
            } else {
                movie = movie!!.copy(isFavorite = isFavorite)
                var isUpdated = false
                withContext(Dispatchers.IO){
                    val affectedRow = dao.updateMovie(movie!!)
                    isUpdated = affectedRow == 1
                }
                emit(isUpdated)
            }
        }
    }
}