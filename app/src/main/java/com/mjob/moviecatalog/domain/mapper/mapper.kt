package com.mjob.moviecatalog.domain.mapper

import android.util.Log
import com.mjob.moviecatalog.BuildConfig
import com.mjob.moviecatalog.data.repository.model.Movie
import com.mjob.moviecatalog.data.repository.model.Show
import com.mjob.moviecatalog.domain.model.Content
import com.mjob.moviecatalog.domain.model.Episode
import com.mjob.moviecatalog.domain.model.Season

fun Movie.toContent(genre: String): Content {
    return Content(
        id = this.id,
        title = this.originalTitle,
        overview = this.overview,
        genre = genre,
        backdropPath = "${BuildConfig.PICTURE_BASE_URL}/${this.backdropPath}",
        posterPath = "${BuildConfig.PICTURE_BASE_URL}/${this.posterPath}",
        releaseDate = this.releaseDate,
        isFavorite = this.isFavorite,
        voteAverage = voteAverage,
        voteCount = this.voteCount?.toInt(),
        trailer = this.youtubeTrailer,
        platforms = this.platforms,
        isMovie = true
    )
}


fun Show.toContent(genre: String): Content {
    return Content(
        id = this.id,
        title = this.originalTitle,
        overview = this.overview,
        genre = genre,
        backdropPath = "${BuildConfig.PICTURE_BASE_URL}/${this.backdropPath}",
        posterPath = "${BuildConfig.PICTURE_BASE_URL}/${this.posterPath}",
        firstAired = this.firstAired,
        isFavorite = this.isFavorite,
        voteAverage = voteAverage,
        voteCount = this.voteCount?.toInt(),
        trailer = this.youtubeTrailer,
        platforms = this.platforms,
        isMovie = false,
        seasons = this.episodes.map {
            Episode(
                episodeNumber = it.episodeNumber,
                firstAired = it.firstAired,
                id = it.id,
                seasonNumber = it.seasonNumber,
                showId = it.showId,
                thumbnailPath = "${BuildConfig.PICTURE_BASE_URL}/${it.thumbnailPath}",
                title = it.title
            )
        }.toSeasons()
    )
}

fun List<Episode>.toSeasons(): List<Season> {
    return this.groupBy { episode ->
        episode.seasonNumber
    }.map { (seasonNumber, episodes) ->
        Season(
            name = "Season $seasonNumber",
            episodes = episodes.sortedBy { it.episodeNumber }
        )
    }
}

