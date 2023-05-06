package com.android.network.domain.repos

import com.android.model.home.*
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.network.states.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun getHome(): Flow<Result<HomeResponse>>
    suspend fun getAllCategories(): Flow<Result<List<Category>>>
    suspend fun getAllBrands(): Flow<Result<List<Brand>>>
    suspend fun getAllProductList(searchProduct: SearchProductRequest): Flow<Result<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Flow<Result<Product>>
    suspend fun register(request: UserRegisterRequest): Flow<Result<UserData>>
    suspend fun login(userLoginRequest: UserLoginRequest): Flow<Result<UserData>>
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Flow<Result<UserData>>
    suspend fun resendOtp(userId: Int): Flow<Result<String>>
    suspend fun getAllAddresses(): Flow<Result<List<UserAddress>>>
    suspend fun addUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>>
    suspend fun updateUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>>
    suspend fun getAllAreas(arabic: Boolean): Flow<Result<List<Area>>>
    suspend fun loadCheckout(checkout: Checkout): Flow<Result<Any>>
    suspend fun getOrdersHistory(arabic: Boolean = false): Flow<Result<List<OrderHistory>>>

}