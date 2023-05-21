package com.mjob.moviecatalog.di

import android.content.Context
import androidx.room.Room
import com.mjob.moviecatalog.BuildConfig
import com.mjob.moviecatalog.data.datasource.CacheableDataSource
import com.mjob.moviecatalog.data.datasource.ReadOnlyDataSource
import com.mjob.moviecatalog.data.datasource.local.Dao
import com.mjob.moviecatalog.data.datasource.local.Database
import com.mjob.moviecatalog.data.datasource.local.LocalDataSource
import com.mjob.moviecatalog.data.datasource.local.typeconverter.EpisodeEntityConverter
import com.mjob.moviecatalog.data.datasource.remote.RemoteDataSource
import com.mjob.moviecatalog.data.repository.MovieRepository
import com.mjob.moviecatalog.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.gson.gson

@Module()
@InstallIn(SingletonComponent::class)
class  AppModule {

    @Provides
    fun providesHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                gson(contentType = ContentType.Application.Json)
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                }
                header("X-RapidAPI-Key", BuildConfig.API_HEADER_KEY)
                header("X-RapidAPI-Host", BuildConfig.API_HEADER_HOST)
            }
        }
    }

    @Provides
    fun providesReadOnlyDataSource(client: HttpClient): ReadOnlyDataSource {
        return RemoteDataSource(client);
    }

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "movie_db"
        ).addTypeConverter(EpisodeEntityConverter())
            .build()
    }

    @Provides
    fun providesDao(database: Database): Dao {
        return database.dao()
    }

    @Provides
    fun providesCacheableDataSource(dao: Dao): CacheableDataSource {
        return LocalDataSource(dao)
    }

    @Provides
    fun providesMovieRepository(
        cacheableDataSource: CacheableDataSource,
        readOnlyDataSource: ReadOnlyDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            localDataSource = cacheableDataSource,
            remoteDataSource = readOnlyDataSource
        )
    }
}