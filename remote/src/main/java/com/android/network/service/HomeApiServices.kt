package com.android.network.service

import com.android.model.home.*
import com.android.model.responses.base.BaseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface HomeApiServices {

    @GET("GetHome")
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>

    @GET("AllCategories")
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>

    @GET("AllBrands")
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>

    @POST("SearchProducts")
    suspend fun getAllProductList(@Body searchProduct: SearchProductRequest): Response<BaseWrapper<List<Product>>>

    @GET("ProductDetails")
    suspend fun getAllProductDetails(@Query("ItemId") ItemId: Int): Response<BaseWrapper<ProductDetail>>

}

