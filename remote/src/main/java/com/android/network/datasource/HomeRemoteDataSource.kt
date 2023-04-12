package com.android.network.datasource

import com.android.model.home.HomeResponse
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.MoviesListResponse
import retrofit2.Response

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>

}