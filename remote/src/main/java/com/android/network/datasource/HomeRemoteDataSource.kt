package com.android.network.datasource

import com.android.model.home.*
import com.android.model.request.AddToCartRequest
import com.android.model.request.ContactUsRequest
import com.android.model.request.GuestLoginRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.BazaarDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeRemoteDataSource {
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>
    suspend fun getAllProductList(searchProductRequest: SearchProductRequest): Response<BaseWrapper<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Response<BaseWrapper<Product>>
    suspend fun loadFiltersAndSorting(queryMap: Map<String, String>): Response<BaseWrapper<SortFilter>>
    suspend fun register(request: UserRegisterRequest): Response<BaseWrapper<UserData>>
    suspend fun updateFcmToken(fcmToken: String): Response<BaseWrapper<*>>
    suspend fun editProfile(request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>>
    suspend fun deleteAccount(): Response<BaseWrapper<Boolean>>
    suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Response<BaseWrapper<Boolean>>

    suspend fun login(userLoginRequest: UserLoginRequest): Response<BaseWrapper<UserData>>
    suspend fun loginGuest(guestLoginRequest: GuestLoginRequest): Response<BaseWrapper<UserData>>
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Response<BaseWrapper<UserData>>
    suspend fun resendOtp(userId: Int): Response<BaseWrapper<Boolean>>
    suspend fun getAllAddresses(): Response<BaseWrapper<List<UserAddress>>>
    suspend fun addUserAddress(userAddress: UserAddress): Response<BaseWrapper<UserAddress>>
    suspend fun updateUserAddress(userAddress: UserAddress): Response<BaseWrapper<UserAddress>>
    suspend fun getAllAreas(): Response<BaseWrapper<List<Area>>>
    suspend fun getOrdersHistory(lastDaysCount: Int): Response<BaseWrapper<List<OrderHistory>>>
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
    suspend fun addToCart(addToCartRequest: AddToCartRequest): Response<BaseWrapper<Boolean>>
    suspend fun deleteFromCart(
        itemDetailId: Int,
        addToWishList: Boolean = false
    ): Response<BaseWrapper<Boolean>>

    suspend fun clearCart(): Response<BaseWrapper<Boolean>>
    suspend fun loadCart(): Response<BaseWrapper<List<Product>>>
    suspend fun updateCartQuantity(itemDetailId: Int, qty: Int): Response<BaseWrapper<Boolean>>

    suspend fun getAboutUs(): Response<BaseWrapper<String>>
    suspend fun submitContractUs(contactUsRequest: ContactUsRequest): Response<BaseWrapper<Boolean>>
    suspend fun getOrdersDetails(orderId: Int): Response<BaseWrapper<OrderHistory>>
    suspend fun requestForgetPassword(phone: String): Response<BaseWrapper<Boolean>>


}