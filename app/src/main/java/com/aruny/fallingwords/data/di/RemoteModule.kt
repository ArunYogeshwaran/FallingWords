package com.aruny.fallingwords.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    fun provideRetrofitBuilder(
        converterFactory: GsonConverterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
    }

    @Provides
    fun provideRetrofit(builder: Retrofit.Builder): Retrofit {
        // TODO: Avoid hard-coded base URL - Make it configurable between release and debug versions
        return builder
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

}