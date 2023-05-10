package com.android.network.service

import com.android.model.home.*
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BaseWrapper
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

    @POST("EditProfile")
    suspend fun editProfile(@Body request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>>

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
    suspend fun getAllAreas(@Query("arabic") arabic: Boolean = false): Response<BaseWrapper<List<Area>>>

    @POST("LoadCheckout")
    suspend fun loadCheckout(@Body checkout: Checkout): Response<BaseWrapper<Any>>

    @POST("OrderHistory")
    suspend fun getOrdersHistory(@Query("arabic") arabic: Boolean = false): Response<BaseWrapper<List<OrderHistory>>>

    @POST("LoadCheckout")
    suspend fun loadCheckout(
        @Query("arabic") arabic: Boolean = false,
        @Body body: LoadCheckoutRequest
    ): Response<BaseWrapper<CheckoutModel>>

    @POST("CreateOrder")
    suspend fun createOrder(
        @Query("arabic") arabic: Boolean = false,
        @Body body: LoadCheckoutRequest
    ): Response<BaseWrapper<CreateOrderModel>>

    @GET("AllBzarz")
    suspend fun getAllBazars(@Query("arabic") arabic: Boolean = false): Response<BaseWrapper<List<Bazar>>>

}

