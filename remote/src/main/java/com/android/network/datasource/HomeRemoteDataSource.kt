package com.android.network.datasource

import com.android.model.home.*
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.MoviesListResponse
import retrofit2.Response

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>
    suspend fun getAllProductList(searchProduct: SearchProductRequest): Response<BaseWrapper<List<Product>>>
}