package  com.bazzar.android.di

import android.content.Context
import android.content.SharedPreferences
import com.android.local.Constants
import com.android.local.PrefersStore
import com.android.local.PrefersStoreImp
import com.android.local.SharedPrefersManager
import com.bazzar.android.common.event.CompDispatcher
import com.bazzar.android.common.event.IODispatcher
import com.bazzar.android.common.event.MainDispatcher
import com.bazzar.android.utils.AppCoroutineDispatchers
import com.android.network.datasource.HomeRemoteDataSource
import com.android.network.datasource.impl.HomeRemoteDataSourceImpl
import com.android.network.service.HomeApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideHomeApiServices(retrofit: Retrofit): HomeApiServices =
        retrofit.create(HomeApiServices::class.java)

    @Singleton
    @Provides
    fun provideHomeRemoteDataSource(remoteApiServices: HomeApiServices): HomeRemoteDataSource =
        HomeRemoteDataSourceImpl(remoteApiServices)

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext appContext: Context): PrefersStore =
        PrefersStoreImp(appContext)

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.sharedPreferencesName, 0)

    @Singleton
    @Provides
    fun provideSharedPrefersManager(sharedPreferences: SharedPreferences): SharedPrefersManager =
        SharedPrefersManager(sharedPreferences)

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Provides
    @IODispatcher
    fun providesIODispatcher(dispatchers: AppCoroutineDispatchers): CoroutineDispatcher {
        return dispatchers.io
    }

    @Provides
    @MainDispatcher
    fun providesMainDispatcher(dispatchers: AppCoroutineDispatchers): CoroutineDispatcher {
        return dispatchers.main
    }

    @Provides
    @CompDispatcher
    fun providesComputationDispatcher(dispatchers: AppCoroutineDispatchers): CoroutineDispatcher {
        return dispatchers.computation
    }
}