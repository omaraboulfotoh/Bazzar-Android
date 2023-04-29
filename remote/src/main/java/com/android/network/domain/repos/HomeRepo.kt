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
    suspend fun register(userData: UserData): Flow<Result<Any>>
    suspend fun login(userLoginRequest: UserLoginRequest): Flow<Result<UserLoginResponse>>
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Flow<Result<Any>>
    suspend fun addUserAddress(userAddress: UserAddress): Flow<Result<Any>>
    suspend fun updateUserAddress(userAddress: UserAddress): Flow<Result<Any>>
    suspend fun loadCheckout(checkout: Checkout): Flow<Result<Any>>

}