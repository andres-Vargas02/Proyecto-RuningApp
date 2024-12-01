package com.uptc.runningapp.di

import com.uptc.runningapp.repositories.RaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RaceModule {

    @Provides
    fun provideRaceRepository(): RaceRepository {
        return RaceRepository
    }
}