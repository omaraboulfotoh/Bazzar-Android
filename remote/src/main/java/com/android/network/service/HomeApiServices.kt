package com.android.network.service

import com.android.model.home.*
import com.android.model.request.AddToCartRequest
import com.android.model.request.ChangePasswordRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
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
import retrofit2.http.QueryMap


interface HomeApiServices {


    // Mobile Requests
    @GET("General/GetHome")
    suspend fun getHome(): Response<BaseWrapper<HomeResponse>>

    @GET("General/AllCategories")
    suspend fun getAllCategories(): Response<BaseWrapper<List<Category>>>

    @GET("General/AllBrands")
    suspend fun getAllBrands(): Response<BaseWrapper<List<Brand>>>

    @GET("General/AllAreas")
    suspend fun getAllAreas(): Response<BaseWrapper<List<Area>>>

    @GET("General/AllBzarz")
    suspend fun getAllBazars(): Response<BaseWrapper<List<BazaarModel>>>

    @GET("General/GetBzarDetails")
    suspend fun getBazaarDetails(@Query("MarketerId") MarketerId: Int): Response<BaseWrapper<BazaarDetailsResponse>>


    // all product APIS
    @POST("Item/SearchProducts")
    suspend fun getAllProductList(@Body searchProduct: SearchProductRequest): Response<BaseWrapper<List<Product>>>

    @GET("Item/ProductDetails")
    suspend fun getAllProductDetails(@Query("ItemId") ItemId: Int): Response<BaseWrapper<Product>>

    @GET("Item/LoadFiltersAndSorting")
    suspend fun loadFiltersAndSorting(@QueryMap queryMap: Map<String, String>): Response<BaseWrapper<SortFilter>>


    // auth APIs for user
    @POST("User/Login")
    suspend fun login(@Body loginRequest: UserLoginRequest): Response<BaseWrapper<UserData>>

    @POST("User/Register")
    suspend fun register(@Body request: UserRegisterRequest): Response<BaseWrapper<UserData>>

    @POST("User/UpdateFcmToken")
    suspend fun updateFcmToken(@Query("fcmToken") fcmToke: String): Response<BaseWrapper<*>>

    @POST("User/EditProfile")
    suspend fun editProfile(@Body request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>>

    @POST("User/DeleteAccount")
    suspend fun deleteAccount(): Response<BaseWrapper<Boolean>>

    @POST("User/ChangePassword")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): Response<BaseWrapper<Boolean>>

    @POST("User/VerifyOtp")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<BaseWrapper<UserData>>

    @POST("User/ResendOTP")
    suspend fun resendOtp(@Query("userId") userId: Int): Response<BaseWrapper<String>>


    // Address APIS
    @GET("UserAddress/GetAllUserAddress")
    suspend fun getAllAddresses(): Response<BaseWrapper<List<UserAddress>>>

    @POST("UserAddress/AddUserAddress")
    suspend fun addUserAddress(@Body userAddress: UserAddress): Response<BaseWrapper<UserAddress>>

    @POST("UserAddress/UpdateUserAddress")
    suspend fun updateUserAddress(@Body userAddress: UserAddress): Response<BaseWrapper<UserAddress>>


    // Orders APIs
    @POST("Order/OrderHistory")
    suspend fun getOrdersHistory(): Response<BaseWrapper<List<OrderHistory>>>

    @POST("Order/LoadCheckout")
    suspend fun loadCheckout(@Body body: LoadCheckoutRequest): Response<BaseWrapper<CheckoutModel>>

    @POST("Order/CreateOrder")
    suspend fun createOrder(@Body body: LoadCheckoutRequest): Response<BaseWrapper<CreateOrderModel>>


    // wishList APIs
    @GET("WishList/LoadMyWishListItems")
    suspend fun getProductWishList(): Response<BaseWrapper<List<Product>>>

    @POST("WishList/AddItemToWishList")
    suspend fun addProductWishList(@Query("ItemId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @POST("WishList/DeleteWishListItem")
    suspend fun deleteProductWishList(@Query("ItemId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @GET("WishList/LoadMyWishListMarketers")
    suspend fun getBazaarsWishList(): Response<BaseWrapper<List<BazaarModel>>>

    @POST("WishList/AddMarketerToWishList")
    suspend fun addBazaarWishList(@Query("MarketerId") ItemId: Int): Response<BaseWrapper<Boolean>>

    @POST("WishList/DeleteWishListMarketer")
    suspend fun deleteBazaarWishList(@Query("MarketerId") ItemId: Int): Response<BaseWrapper<Boolean>>


    // Cart APIs
    @POST("Cart/AddToCart")
    suspend fun addToCart(@Body addToCartRequest: AddToCartRequest): Response<BaseWrapper<Boolean>>

    @POST("Cart/DeleteCartItem?=33")
    suspend fun deleteFromCart(@Query("ItemDetailId") itemDetailId: Int): Response<BaseWrapper<Boolean>>

    @GET("Cart/LoadMyCart")
    suspend fun loadCart(): Response<BaseWrapper<List<Product>>>

    @GET("Cart/ClearCart")
    suspend fun clearCart(): Response<BaseWrapper<Boolean>>

    @POST("Cart/UpdateCartQty")
    suspend fun updateCartQuantity(
        @Query("ItemDetailId") itemDetailId: Int, @Query("Qty") qty: Int
    ): Response<BaseWrapper<Boolean>>


}

