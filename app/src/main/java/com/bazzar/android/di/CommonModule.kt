package com.bazzar.android.di

import android.content.Context
import com.bazzar.android.presentation.app.GlobalState
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.BazzarApplication
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BazzarApplication {
        return app as BazzarApplication
    }

    @Singleton
    @Provides
    fun provideGlobalState(): IGlobalState {
        return GlobalState()
    }

    @Singleton
    @Provides
    fun resourceProvider(@ApplicationContext context: Context): IResourceProvider =
        ResourceProvider(context)
}
