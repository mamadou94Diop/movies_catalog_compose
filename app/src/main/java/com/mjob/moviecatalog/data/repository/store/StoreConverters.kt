package com.mjob.moviecatalog.data.repository.store

import com.mjob.moviecatalog.data.datasource.local.model.EpisodeEntity
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import com.mjob.moviecatalog.data.repository.model.Episode
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Show
import org.mobilenativefoundation.store.store5.Converter

fun converterForMovie() = Converter
    .Builder<List<MovieResponse>, List<Movie>, List<MovieEntity>>()
    .fromNetworkToOutput { network: List<MovieResponse> ->
        network.map {
            with(it) {
                Movie(
                    backdropPath = backdropPath,
                    genres = genres,
                    id = id,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate
                )
            }

        }
    }
    .fromOutputToLocal { output: List<Movie> ->
        output.map {
            with(it) {
                MovieEntity(
                    backdropPath = backdropPath,
                    genres = genres.joinToString(","),
                    id = id,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                )
            }
        }
    }
    .fromLocalToOutput { local: List<MovieEntity> ->
        local.map {
            with(it) {
                Movie(
                    backdropPath = backdropPath,
                    genres = genres.split(","),
                    id = id,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    releaseDate = releaseDate,
                    isFavorite = isFavorite
                )
            }
        }
    }
    .build()


fun converterForShow() = Converter
    .Builder<List<ShowResponse>, List<Show>, List<ShowEntity>>()
    .fromNetworkToOutput { network: List<ShowResponse> ->
        network.map {
            with(it) {
                Show(
                    backdropPath = backdropPath,
                    genres = genres,
                    id = id,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    firstAired = firstAired
                )
            }

        }
    }
    .fromOutputToLocal { output: List<Show> ->
        output.map {
            with(it) {
                ShowEntity(
                    backdropPath = backdropPath,
                    genres = genres.joinToString(","),
                    id = id,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    firstAired = firstAired,
                    episodes = episodes.map { episode ->
                        with(episode){
                            EpisodeEntity(
                                episodeNumber = episodeNumber,
                                firstAired = firstAired,
                                id = id,
                                seasonNumber = seasonNumber,
                                showId = showId,
                                thumbnailPath = thumbnailPath,
                                title = title

                            )
                        }
                    }
                )
            }
        }
    }
    .fromLocalToOutput { local: List<ShowEntity> ->
        local.map {
            with(it) {
                Show(
                    backdropPath = backdropPath,
                    genres = genres.split(","),
                    id = id,
                    originalTitle = originalTitle,
                    overview = overview,
                    posterPath = posterPath,
                    firstAired = firstAired,
                    isFavorite = isFavorite,
                    episodes = episodes.map { episodeEntity ->
                        with(episodeEntity){
                            Episode(
                                episodeNumber = episodeNumber,
                                firstAired = firstAired,
                                id = id,
                                seasonNumber = seasonNumber,
                                showId = showId,
                                thumbnailPath = thumbnailPath,
                                title = title

                            )
                        }
                    }
                )
            }
        }
    }
    .build()
