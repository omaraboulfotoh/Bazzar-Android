package com.android.network.datasource.impl;

import com.android.network.service.HomeApiServices;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MovieRemoteDataSourceImp_Factory implements Factory<HomeRemoteDataSourceImpl> {
  private final Provider<HomeApiServices> apiServicesProvider;

  public MovieRemoteDataSourceImp_Factory(Provider<HomeApiServices> apiServicesProvider) {
    this.apiServicesProvider = apiServicesProvider;
  }

  @Override
  public HomeRemoteDataSourceImpl get() {
    return newInstance(apiServicesProvider.get());
  }

  public static MovieRemoteDataSourceImp_Factory create(
      Provider<HomeApiServices> apiServicesProvider) {
    return new MovieRemoteDataSourceImp_Factory(apiServicesProvider);
  }

  public static HomeRemoteDataSourceImpl newInstance(HomeApiServices apiServices) {
    return new HomeRemoteDataSourceImpl(apiServices);
  }
}
