package com.android.network.datasource.impl

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.HomeResponse
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.MoviesListResponse
import com.android.network.datasource.HomeRemoteDataSource
import com.android.network.service.HomeApiServices
import retrofit2.Response
import javax.inject.Inject


class HomeRemoteDataSourceImpl @Inject constructor(private val apiServices: HomeApiServices) :
    HomeRemoteDataSource {
    override suspend fun getHome() = apiServices.getHome()
    override suspend fun getAllCategories() = apiServices.getAllCategories()
    override suspend fun getAllBrands() = apiServices.getAllBrands()
}