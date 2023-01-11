package com.example.composeproject.di

import android.app.Application
import com.example.composeproject.data.AppDatabase
import com.example.composeproject.data.ArtDAO
import com.example.composeproject.data.remote.SearchAnimeApi
import com.example.composeproject.data.repository.ArtRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideArtRepository(
        artDAO: ArtDAO
    ): ArtRepository{
        return ArtRepository(artDAO = artDAO)
    }

    @Singleton
    @Provides
    fun provideArtDatabase(application: Application):AppDatabase{
        return AppDatabase.getInstance(context = application)
    }

    @Singleton
    @Provides
    fun provideArtDao(appDatabase: AppDatabase): ArtDAO{
        return appDatabase.artDao()
    }

    @Provides
    @Singleton
    fun provideAnimeApi(): SearchAnimeApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
}