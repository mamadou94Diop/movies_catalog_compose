package com.mjob.moviecatalog.data.datasource.local

import com.mjob.moviecatalog.data.datasource.CacheableDataSource
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: Dao) : CacheableDataSource {

    override fun getMovies(): Flow<List<MovieEntity>> {
        return dao.getMovies()
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        dao.insertMovies(movies)
    }

    override suspend fun deleteMovies() {
        dao.deleteMovies()
    }

    override fun getShows(): Flow<List<ShowEntity>> {
        return dao.getShows()
    }

    override suspend fun insertShows(shows: List<ShowEntity>) {
        dao.insertShows(shows)
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

    override fun getShow(id: Int): Flow<ShowEntity?> {
        return dao.getShow(id)
    }

    override fun getMovie(id: Int): Flow<MovieEntity?> {
        return dao.getMovie(id)

    }

    override suspend fun updateShow(show: ShowEntity) {
        dao.updateShow(show)
    }

    override suspend fun updateMovie(movie: MovieEntity) {
        dao.updateMovie(movie)
    }
}