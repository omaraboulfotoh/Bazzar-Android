package com.android.network.datasource

import com.android.model.home.*
import com.android.model.responses.base.BaseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>
    suspend fun getAllProductList(searchProduct: SearchProductRequest): Response<BaseWrapper<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Response<BaseWrapper<Product>>
    suspend fun register(userData: UserData): Response<BaseWrapper<Any>>
    suspend fun login(userLoginRequest: UserLoginRequest): Response<BaseWrapper<UserLoginResponse>>
    suspend fun verifyOtp( verifyOtpRequest: VerifyOtpRequest): Response<BaseWrapper<Any>>
    suspend fun addUserAddress( userAddress:UserAddress):Response<BaseWrapper<Any>>
    suspend fun updateUserAddress( userAddress:UserAddress):Response<BaseWrapper<Any>>
    suspend fun loadCheckout( checkout:Checkout):Response<BaseWrapper<Any>>

}