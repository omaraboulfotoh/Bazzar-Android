package com.android.network.domain.usecases;

import com.android.network.domain.repos.HomeRepo;
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
public final class MovieUseCase_Factory implements Factory<HomeUseCase> {
  private final Provider<HomeRepo> movieRepoProvider;

  public MovieUseCase_Factory(Provider<HomeRepo> movieRepoProvider) {
    this.movieRepoProvider = movieRepoProvider;
  }

  @Override
  public HomeUseCase get() {
    return newInstance(movieRepoProvider.get());
  }

  public static MovieUseCase_Factory create(Provider<HomeRepo> movieRepoProvider) {
    return new MovieUseCase_Factory(movieRepoProvider);
  }

  public static HomeUseCase newInstance(HomeRepo movieRepo) {
    return new HomeUseCase(movieRepo);
  }
}
