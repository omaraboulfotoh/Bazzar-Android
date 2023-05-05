package com.android.network.service

import com.android.model.home.*
import com.android.model.order.OrderHistory
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
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
    suspend fun getAllProductDetails(@Query("ItemId") ItemId: Int): Response<BaseWrapper<Product>>

    @POST("Login")
    suspend fun login(@Body userLoginRequest: UserLoginRequest): Response<BaseWrapper<UserData>>

    @POST("Register")
    suspend fun register(@Body request: UserRegisterRequest): Response<BaseWrapper<UserData>>

    @POST("VerifyOtp")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<BaseWrapper<UserData>>

    @POST("AddUserAddress")
    suspend fun addUserAddress(@Body userAddress: UserAddress): Response<BaseWrapper<Any>>

    @POST("UpdateUserAddress")
    suspend fun updateUserAddress(@Body userAddress: UserAddress): Response<BaseWrapper<Any>>

    @POST("LoadCheckout")
    suspend fun loadCheckout(@Body checkout: Checkout): Response<BaseWrapper<Any>>

    @POST("OrderHistory")
    suspend fun getOrdersHistory(@Query("arabic") arabic: Boolean = false): Response<BaseWrapper<List<OrderHistory>>>

}

