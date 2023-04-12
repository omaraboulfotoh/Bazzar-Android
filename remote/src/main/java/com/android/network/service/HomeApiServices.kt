package com.android.network.service

import com.android.model.home.HomeResponse
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface HomeApiServices {

    @GET("GetHome")
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>

}

