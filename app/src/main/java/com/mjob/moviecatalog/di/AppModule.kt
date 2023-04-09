package com.mjob.moviecatalog.di

import com.mjob.moviecatalog.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.serialization.gson.gson

@Module()
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                gson(contentType = ContentType.Application.Json)
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BuildConfig.API_BASE_URL
                }
                header("X-RapidAPI-Key", BuildConfig.API_HEADER_KEY)
                header("X-RapidAPI-Host", BuildConfig.API_HEADER_HOST)
            }
        }
    }
}