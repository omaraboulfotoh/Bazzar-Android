package com.bazzar.android.di

import com.android.network.datasource.HomeRemoteDataSource
import com.android.network.domain.repos.HomeRepo
import com.android.network.domain.repos.impl.HomeRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideHomeRepo(remoteDataSource: HomeRemoteDataSource): HomeRepo =
        HomeRepoImpl(remoteDataSource)

}
