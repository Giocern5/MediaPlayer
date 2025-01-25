package com.suno.android.sunointerview.di

import android.content.Context
import com.suno.android.sunointerview.data.repository.MediaFeedRepository
import com.suno.android.sunointerview.data.repository.MediaFeedRepositoryImpl
import com.suno.android.sunointerview.network.MediaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return instance.create(MediaService::class.java)
    }

    @Provides
    fun MediaFeedRepository(service: MediaService): MediaFeedRepository {
        return MediaFeedRepositoryImpl(service)
    }

}