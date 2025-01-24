package com.suno.android.sunointerview.di

import android.content.Context
import com.suno.android.sunointerview.network.MediaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://apitest.suno.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRetrofit() : MediaService {
        val instance = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(generateConverterFactory())
            .build()

        return instance.create(MediaService::class.java)
    }

    // is this needed? revisit
    private fun generateConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val contentType = MediaType.get("application/json")
        return json.asConverterFactory(contentType)
    }
}