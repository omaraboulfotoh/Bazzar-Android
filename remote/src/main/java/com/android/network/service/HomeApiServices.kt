package com.android.network.service

import com.android.model.home.*
import com.android.model.request.AddToCartRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BaseWrapper
import com.android.model.responses.base.BazaarDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    @FormUrlEncoded
    suspend fun login(
        @Field("Phone") phone: String,
        @Field("Password") password: String,
    ): Response<BaseWrapper<UserData>>

    @POST("Register")
    suspend fun register(@Body request: UserRegisterRequest): Response<BaseWrapper<UserData>>

    @POST("UpdateFcmToken")
    suspend fun updateFcmToken(@Query("fcmToken") fcmToke: String): Response<BaseWrapper<*>>

    @POST("EditProfile")
    suspend fun editProfile(@Body request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>>

    @POST("DeleteAccount")
    suspend fun deleteAccount(): Response<BaseWrapper<Boolean>>

    @POST("ChangePassword")
    @FormUrlEncoded
    suspend fun changePassword(
        @Field("OldPassword") currentPassword: String,
        @Field("NewPassword") newPassword: String
    ): Response<BaseWrapper<Boolean>>

    @POST("VerifyOtp")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<BaseWrapper<UserData>>

    @POST("ResendOTP")
    suspend fun resendOtp(@Query("userId") userId: Int): Response<BaseWrapper<String>>

    @GET("GetAllUserAddress")
    suspend fun getAllAddresses(): Response<BaseWrapper<List<UserAddress>>>

    @POST("AddUserAddress")
    suspend fun addUserAddress(@Body userAddress: UserAddress): Response<BaseWrapper<UserAddress>>

    @POST("UpdateUserAddress")
    suspend fun updateUserAddress(@Body userAddress: UserAddress): Response<BaseWrapper<UserAddress>>

    @GET("AllAreas")
    suspend fun getAllAreas(): Response<BaseWrapper<List<Area>>>

    @POST("OrderHistory")
    suspend fun getOrdersHistory(): Response<BaseWrapper<List<OrderHistory>>>

    @POST("LoadCheckout")
    suspend fun loadCheckout(@Body body: LoadCheckoutRequest): Response<BaseWrapper<CheckoutModel>>

    @POST("CreateOrder")
    suspend fun createOrder(@Body body: LoadCheckoutRequest): Response<BaseWrapper<CreateOrderModel>>

    @GET("AllBzarz")
    suspend fun getAllBazars(): Response<BaseWrapper<List<BazaarModel>>>

    @GET("GetBzarDetails")
    suspend fun getBazaarDetails(@Query("MarketerId") MarketerId: Int): Response<BaseWrapper<BazaarDetailsResponse>>

    @GET("https://api.bzaaarz.com/WishList/LoadMyWishListItems")
    suspend fun getProductWishList(): Response<BaseWrapper<List<Product>>>

    @POST("https://api.bzaaarz.com/WishList/AddItemToWishList")
    suspend fun addProductWishList(@Query("ItemId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @POST("https://api.bzaaarz.com/WishList/DeleteWishListItem")
    suspend fun deleteProductWishList(@Query("ItemId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @GET("https://api.bzaaarz.com/WishList/LoadMyWishListMarketers")
    suspend fun getBazaarsWishList(): Response<BaseWrapper<List<BazaarModel>>>

    @POST("https://api.bzaaarz.com/WishList/AddMarketerToWishList")
    suspend fun addBazaarWishList(@Query("MarketerId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @POST("https://api.bzaaarz.com/WishList/DeleteWishListMarketer")
    suspend fun deleteBazaarWishList(@Query("MarketerId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @POST("https://api.bzaaarz.com/Cart/AddToCart")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<BaseWrapper<Boolean>>

    @POST("https://api.bazzargate.com/Cart/DeleteCartItem?=33")
    suspend fun deleteFromCart(@Query("ItemDetailId") itemDetailId: Int): Response<BaseWrapper<Boolean>>

    @GET("https://api.bzaaarz.com/Cart/LoadMyCart")
    suspend fun loadCart(): Response<BaseWrapper<List<Product>>>

    @POST("https://api.bazzargate.com/Cart/UpdateCartQty")
    suspend fun updateCartQuantity(
        @Query("ItemDetailId") itemDetailId: Int,
        @Query("Qty") qty: Int
    ): Response<BaseWrapper<Boolean>>


}

