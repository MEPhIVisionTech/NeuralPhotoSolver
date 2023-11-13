package ru.tanexc.photosolver.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tanexc.photosolver.data.repository.SettingsRepositoryImpl
import ru.tanexc.photosolver.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository


}