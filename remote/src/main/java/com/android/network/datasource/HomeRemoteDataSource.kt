package com.android.network.datasource

import com.android.model.home.*
import com.android.model.request.CartItemRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.BazaarDetailsResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>
    suspend fun getAllProductList(request: SearchProductRequest): Response<BaseWrapper<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Response<BaseWrapper<Product>>
    suspend fun register(request: UserRegisterRequest): Response<BaseWrapper<UserData>>
    suspend fun editProfile(request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>>
    suspend fun deleteAccount(): Response<BaseWrapper<Boolean>>
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Response<BaseWrapper<Boolean>>

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
    suspend fun getAllBazars(): Response<BaseWrapper<List<BazaarModel>>>
    suspend fun getBazaarDetails(bazaarId: Int): Response<BaseWrapper<BazaarDetailsResponse>>
    suspend fun getProductWishList(): Response<BaseWrapper<List<Product>>>
    suspend fun addProductWishList(itemId: Int): Response<BaseWrapper<Boolean>>
    suspend fun deleteProductWishList(itemId: Int): Response<BaseWrapper<Boolean>>
    suspend fun getBazaarsWishList(): Response<BaseWrapper<List<BazaarModel>>>
    suspend fun addBazaarWishList(marketerId: Int): Response<BaseWrapper<Boolean>>
    suspend fun deleteBazaarWishList(marketerId: Int): Response<BaseWrapper<Boolean>>


}