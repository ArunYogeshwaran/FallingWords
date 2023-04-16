package com.aruny.fallingwords.data.di

import com.aruny.fallingwords.data.rest.WordsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

const val BASE_URL = "https://gist.githubusercontent.com/"

@Module
@InstallIn(SingletonComponent::class)
class WordsModule {
    @Provides
    @Singleton
    fun providerWordsApi(retrofit: Retrofit): WordsApi {
        return retrofit.create(WordsApi::class.java)
    }
}