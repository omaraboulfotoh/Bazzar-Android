package com.android.network.domain.repos.impl;

import com.android.network.datasource.HomeRemoteDataSource;
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
public final class MovieRepoImpl_Factory implements Factory<HomeRepoImpl> {
  private final Provider<HomeRemoteDataSource> movieRemoteDataSourceProvider;

  public MovieRepoImpl_Factory(Provider<HomeRemoteDataSource> movieRemoteDataSourceProvider) {
    this.movieRemoteDataSourceProvider = movieRemoteDataSourceProvider;
  }

  @Override
  public HomeRepoImpl get() {
    return newInstance(movieRemoteDataSourceProvider.get());
  }

  public static MovieRepoImpl_Factory create(
      Provider<HomeRemoteDataSource> movieRemoteDataSourceProvider) {
    return new MovieRepoImpl_Factory(movieRemoteDataSourceProvider);
  }

  public static HomeRepoImpl newInstance(HomeRemoteDataSource movieRemoteDataSource) {
    return new HomeRepoImpl(movieRemoteDataSource);
  }
}
