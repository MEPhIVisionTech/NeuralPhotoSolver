package ru.tanexc.photosolver.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.tanexc.photosolver.core.HOST
import ru.tanexc.photosolver.core.PORT
import ru.tanexc.photosolver.domain.ImageApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://${HOST}:${PORT}")
        .build()

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): ImageApi = retrofit.create(ImageApi::class.java)
}