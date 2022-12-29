package com.example.composeproject.di

import android.app.Application
import com.example.composeproject.data.AppDatabase
import com.example.composeproject.data.ArtDAO
import com.example.composeproject.data.repository.ArtRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}