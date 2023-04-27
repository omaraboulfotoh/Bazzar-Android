package com.android.network.domain.repos

import com.android.model.home.*
import com.android.network.states.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun getHome(): Flow<Result<HomeResponse>>
    suspend fun getAllCategories(): Flow<Result<List<Category>>>
    suspend fun getAllBrands(): Flow<Result<List<Brand>>>
    suspend fun getAllProductList(searchProduct: SearchProductRequest): Flow<Result<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Flow<Result<ProductDetail>>
}