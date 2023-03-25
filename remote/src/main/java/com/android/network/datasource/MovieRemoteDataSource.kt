package com.android.network.datasource

import com.android.model.movie.ProductModel
import com.android.model.responses.base.MoviesListResponse
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getTrendMovies(
        apiKey: String,
        page: Int,
    ): Response<MoviesListResponse>

    suspend fun getMovieDetails(
        apiKey: String,
        movieId: Int,
    ): Response<ProductModel>

}