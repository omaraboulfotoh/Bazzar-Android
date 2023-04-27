package com.android.network.domain.usecases

import com.android.model.home.SearchProductRequest
import com.android.network.domain.repos.HomeRepo
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val movieRepo: HomeRepo) {

    suspend fun getHome() = movieRepo.getHome()
    suspend fun getAllCategories() = movieRepo.getAllCategories()
    suspend fun getAllBrands() = movieRepo.getAllBrands()
    suspend fun getAllProductList(searchProduct: SearchProductRequest) = movieRepo.getAllProductList(searchProduct)
    suspend fun getAllProductDetails(productId: Int) = movieRepo.getAllProductDetails(productId)
}