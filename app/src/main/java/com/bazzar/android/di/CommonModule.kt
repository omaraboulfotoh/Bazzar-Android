package com.bazzar.android.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.bazzar.android.BazzarApplication
import com.bazzar.android.presentation.app.GlobalState
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.utils.DefaultLocationTracker
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.LocationTracker
import com.bazzar.android.utils.ResourceProvider
import com.bazzar.android.utils.remoteconfig.FirebaseRemoteConfiguration
import com.bazzar.android.utils.remoteconfig.RemoteConfiguration
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
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
    fun firebaseConfig() = FirebaseRemoteConfig.getInstance()

    @Singleton
    @Provides
    fun firebaseRemoteConfiguration(
        @ApplicationContext context: Context,
        config: FirebaseRemoteConfig,
    ): RemoteConfiguration = FirebaseRemoteConfiguration(context, config).apply {
        init()
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationTracker = DefaultLocationTracker(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )
}
