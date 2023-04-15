package com.android.network.datasource

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.HomeResponse
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.MoviesListResponse
import retrofit2.Response

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>

}