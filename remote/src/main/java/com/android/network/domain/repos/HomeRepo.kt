package com.android.network.domain.repos

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
import com.android.network.states.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface HomeRepo {
    suspend fun getHome(): Flow<Result<HomeResponse>>
    suspend fun getAllCategories(): Flow<Result<List<Category>>>
    suspend fun getAllBrands(): Flow<Result<List<Brand>>>
    suspend fun getAllProductList(searchProduct: SearchProductRequest): Flow<Result<List<Product>>>
    suspend fun getAllProductDetails(productId: Int): Flow<Result<Product>>
    suspend fun loadFiltersAndSorting(queryMap: Map<String, String>): Flow<Result<SortFilter>>
    suspend fun register(request: UserRegisterRequest): Flow<Result<UserData>>
    suspend fun updateFcmToken(fcmToken: String): Flow<Result<*>>
    suspend fun editProfile(request: UserRegisterRequest): Flow<Result<EditProfileResponse>>
    suspend fun deleteAccount(): Flow<Result<Boolean>>
    suspend fun changePassword(currentPassword: String, newPassword: String): Flow<Result<Boolean>>
    suspend fun login(userLoginRequest: UserLoginRequest): Flow<Result<UserData>>
    suspend fun loginGuest(guestLoginRequest: GuestLoginRequest): Flow<Result<UserData>>

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Flow<Result<UserData>>
    suspend fun resendOtp(userId: Int): Flow<Result<Boolean>>
    suspend fun getAllAddresses(): Flow<Result<List<UserAddress>>>
    suspend fun addUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>>
    suspend fun updateUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>>
    suspend fun getAllAreas(): Flow<Result<List<Area>>>
    suspend fun getOrdersHistory(lastDaysCount: Int): Flow<Result<List<OrderHistory>>>
    suspend fun loadCheckout(request: LoadCheckoutRequest): Flow<Result<CheckoutModel>>
    suspend fun createOrder(request: LoadCheckoutRequest): Flow<Result<CreateOrderModel>>
    suspend fun getAllBazars(): Flow<Result<List<BazaarModel>>>
    suspend fun getBazaarDetails(bazaarId: Int): Flow<Result<BazaarDetailsResponse>>
    suspend fun getProductWishList(): Flow<Result<List<Product>>>
    suspend fun addProductWishList(itemId: Int): Flow<Result<Boolean>>
    suspend fun deleteProductWishList(itemId: Int): Flow<Result<Boolean>>
    suspend fun getBazaarsWishList(): Flow<Result<List<BazaarModel>>>
    suspend fun addBazaarWishList(marketerId: Int): Flow<Result<Boolean>>
    suspend fun deleteBazaarWishList(marketerId: Int): Flow<Result<Boolean>>
    suspend fun addToCart(addToCartRequest: AddToCartRequest): Flow<Result<Boolean>>
    suspend fun deleteFromCart(
        itemDetailId: Int,
        addToWishList: Boolean = false
    ): Flow<Result<Boolean>>

    suspend fun loadCart(): Flow<Result<List<Product>>>
    suspend fun clearCart(): Flow<Result<Boolean>>
    suspend fun updateCartQuantity(itemDetailId: Int, qty: Int): Flow<Result<Boolean>>
    suspend fun getAboutUs(): Flow<Result<String>>
    suspend fun submitContractUs(contactUsRequest: ContactUsRequest): Flow<Result<Boolean>>
    suspend fun getOrdersDetails(orderId: Int): Flow<Result<OrderHistory>>
    suspend fun requestForgetPassword(phone: String): Flow<Result<Boolean>>
    suspend fun deleteAddress(userAddressId: Int): Flow<Result<Boolean>>


}