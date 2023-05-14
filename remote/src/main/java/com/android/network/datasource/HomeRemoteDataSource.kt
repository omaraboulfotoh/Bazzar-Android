package com.android.network.datasource

import com.android.model.home.*
import com.android.model.request.CartItemRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BaseWrapper
import retrofit2.Response

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>
    suspend fun getAllProductList(request: SearchProductRequest): Response<BaseWrapper<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Response<BaseWrapper<Product>>
    suspend fun register(request: UserRegisterRequest): Response<BaseWrapper<UserData>>
    suspend fun editProfile(request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>>
    suspend fun deleteAccount(): Response<BaseWrapper<Boolean>>
    suspend fun changePassword(currentPassword: String, newPassword: String): Response<BaseWrapper<Boolean>>
    suspend fun login(request: UserLoginRequest): Response<BaseWrapper<UserData>>
    suspend fun verifyOtp(request: VerifyOtpRequest): Response<BaseWrapper<UserData>>
    suspend fun resendOtp(userId: Int): Response<BaseWrapper<String>>
    suspend fun getAllAddresses(): Response<BaseWrapper<List<UserAddress>>>
    suspend fun addUserAddress(userAddress: UserAddress): Response<BaseWrapper<UserAddress>>
    suspend fun updateUserAddress(userAddress: UserAddress): Response<BaseWrapper<UserAddress>>
    suspend fun getAllAreas(): Response<BaseWrapper<List<Area>>>
    suspend fun loadCheckout(checkout: Checkout): Response<BaseWrapper<Any>>
    suspend fun getOrdersHistory(): Response<BaseWrapper<List<OrderHistory>>>
    suspend fun loadCheckout(request: LoadCheckoutRequest): Response<BaseWrapper<CheckoutModel>>
    suspend fun createOrder(request: LoadCheckoutRequest): Response<BaseWrapper<CreateOrderModel>>
    suspend fun getAllBazars(): Response<BaseWrapper<List<Bazar>>>

}