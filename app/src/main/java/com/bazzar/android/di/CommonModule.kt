package com.bazzar.android.di

import android.content.Context
import android.location.Geocoder
import com.bazzar.android.BazzarApplication
import com.bazzar.android.presentation.app.GlobalState
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.ResourceProvider
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
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

    @Singleton
    @Provides
    fun providesGeoCoder(@ApplicationContext context: Context): Geocoder =
        Geocoder(context, Locale.getDefault())

    @Singleton
    @Provides
    fun providesPlacesClient(@ApplicationContext context: Context): PlacesClient =
        Places.createClient(context)
}
