package com.aruny.fallingwords.data.di

import com.aruny.fallingwords.data.WordsRepository
import com.aruny.fallingwords.data.WordsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataModule {

    @Binds
    fun bindWordsRepository(wordsRepositoryImpl: WordsRepositoryImpl): WordsRepository
}