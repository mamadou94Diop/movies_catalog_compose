package com.mjob.moviecatalog.data.repository

import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Show
import com.mjob.moviecatalog.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<Result<List<Movie>>>
    fun getShows(): Flow<Result<List<Show>>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getFavoriteShows(): Flow<List<Show>>
    fun setFavoriteShow(id: Int, isFavorite: Boolean): Flow<Boolean>
    fun setFavoriteMovie(id: Int, isFavorite: Boolean): Flow<Boolean>
    fun getMovie(id : Int): Flow<Movie?>
    fun getShow(id : Int): Flow<Show?>

    fun getTrailers() : Flow<Result<List<Trailer>>>
}