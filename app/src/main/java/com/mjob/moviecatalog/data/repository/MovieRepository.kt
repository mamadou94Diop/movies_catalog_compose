package com.mjob.moviecatalog.data.repository

interface MovieRepository {
    fun getGenres()
    fun getFavoriteMovies()
    fun addMovieToFavorites()
    fun removeMovieFromFavorites(id: String)
}