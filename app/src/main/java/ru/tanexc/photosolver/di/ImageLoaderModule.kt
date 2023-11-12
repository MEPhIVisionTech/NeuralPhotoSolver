package ru.tanexc.notegraph.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tanexc.photosolver.ImageHelper
import ru.tanexc.photosolver.ImageLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Singleton
    @Provides
    fun provideImageHelper(
        @ApplicationContext context: Context
    ): ImageHelper = ImageLoader(context)

}