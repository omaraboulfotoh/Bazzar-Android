package com.android.network.domain.repos

import com.android.model.home.*
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.MoviesListResponse
import com.android.network.states.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeRepo {
    suspend fun getHome(): Flow<Result<HomeResponse>>
    suspend fun getAllCategories(): Flow<Result<List<Category>>>
    suspend fun getAllBrands(): Flow<Result<List<Brand>>>
    suspend fun getAllProductList(searchProduct:SearchProductRequest): Flow<Result<List<Product>>>
}