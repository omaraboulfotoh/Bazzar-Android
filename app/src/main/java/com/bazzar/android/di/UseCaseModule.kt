package com.bazzar.android.di

import com.android.network.domain.repos.HomeRepo
import com.android.network.domain.usecases.HomeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    /**
     * All UseCases
     */
    @Provides
    fun provideHomeUseCase(userRepo: HomeRepo) = HomeUseCase(userRepo)

}