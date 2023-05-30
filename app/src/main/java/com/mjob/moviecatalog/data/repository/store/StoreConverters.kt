package com.mjob.moviecatalog.data.repository.store

import com.mjob.moviecatalog.data.datasource.local.model.EpisodeEntity
import com.mjob.moviecatalog.data.datasource.local.model.MovieEntity
import com.mjob.moviecatalog.data.datasource.local.model.PlatformEntity
import com.mjob.moviecatalog.data.datasource.local.model.ShowEntity
import com.mjob.moviecatalog.data.datasource.remote.model.EpisodeDataResponse
import com.mjob.moviecatalog.data.datasource.remote.model.MovieResponse
import com.mjob.moviecatalog.data.datasource.remote.model.PlatformResponse
import com.mjob.moviecatalog.data.datasource.remote.model.ShowResponse
import com.mjob.moviecatalog.data.repository.model.Episode
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Platform
import com.mjob.moviecatalog.data.repository.model.Show
import org.mobilenativefoundation.store.store5.Converter

fun converterForMovie() = Converter
    .Builder<List<MovieResponse>, List<Movie>, List<MovieEntity>>()
    .fromNetworkToOutput { network: List<MovieResponse> ->
        network.map {
            it.toMovie()

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
            it.toMovie()
        }
    }
    .build()

fun MovieEntity.toMovie(): Movie {
    return Movie(
        backdropPath = backdropPath,
        genres = genres.split(","),
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        isFavorite = isFavorite,
        voteAverage = voteAverage,
        voteCount = voteCount,
        youtubeTrailer = youtubeTrailer
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        backdropPath = backdropPath,
        genres = genres.joinToString(","),
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        isFavorite = isFavorite,
        voteAverage = voteAverage,
        voteCount = voteCount,
        youtubeTrailer = youtubeTrailer
    )
}

fun MovieResponse.toMovie(): Movie {
    return Movie(
        backdropPath = backdropPath,
        genres = genres,
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate
    )
}

fun EpisodeDataResponse.toEpisode(): Episode {
    return with(this) {
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

fun ShowResponse.toShow(): Show {
    return with(this) {
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

fun Show.toShowEntity(): ShowEntity {
    return with(this) {
        ShowEntity(
            backdropPath = backdropPath,
            genres = genres.joinToString(","),
            id = id,
            originalTitle = originalTitle,
            overview = overview,
            posterPath = posterPath,
            firstAired = firstAired,
            episodes = episodes.map { episode ->
                with(episode) {
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
            },
            voteAverage = voteAverage,
            voteCount = voteCount,
            youtubeTrailer = youtubeTrailer,
            isFavorite = isFavorite,
        )
    }
}

fun ShowEntity.toShow(): Show {
    return with(this) {
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
                with(episodeEntity) {
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
            },
            voteAverage = voteAverage,
            voteCount = voteCount,
            youtubeTrailer = youtubeTrailer
        )
    }
}

fun converterForShow() = Converter
    .Builder<List<ShowResponse>, List<Show>, List<ShowEntity>>()
    .fromNetworkToOutput { network: List<ShowResponse> ->
        network.map {
            it.toShow()
        }
    }
    .fromOutputToLocal { output: List<Show> ->
        output.map {
            it.toShowEntity()
        }
    }
    .fromLocalToOutput { local: List<ShowEntity> ->
        local.map {
            it.toShow()
        }
    }
    .build()

fun converterForPlatform() = Converter
    .Builder<List<PlatformResponse>, List<Platform>, List<PlatformEntity>>()
    .fromNetworkToOutput { network: List<PlatformResponse> ->
        network.map {
            it.toPlatform()
        }
    }
    .fromOutputToLocal { output: List<Platform> ->
        output.map {
            it.toPlatformEntity()
        }
    }
    .fromLocalToOutput { local: List<PlatformEntity> ->
        local.map {
            it.toPlatform()
        }
    }
    .build()

private fun PlatformEntity.toPlatform(): Platform {
    return Platform(displayName = this.displayName, source = this.source, type = this.type)
}

private fun Platform.toPlatformEntity(): PlatformEntity {
    return PlatformEntity(displayName = this.displayName, source = this.source, type = this.type)
}

private fun PlatformResponse.toPlatform(): Platform {
    return Platform(displayName = this.displayName, source = this.source, type = this.type)
}


