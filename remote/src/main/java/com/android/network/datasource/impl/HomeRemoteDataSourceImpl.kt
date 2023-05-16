package com.android.network.datasource.impl

import com.android.model.home.*
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BaseWrapper
import com.android.network.datasource.HomeRemoteDataSource
import com.android.network.service.HomeApiServices
import retrofit2.Response
import javax.inject.Inject


class HomeRemoteDataSourceImpl @Inject constructor(private val apiServices: HomeApiServices) :
    HomeRemoteDataSource {
    override suspend fun getHome() = apiServices.getHome()
    override suspend fun getAllCategories() = apiServices.getAllCategories()
    override suspend fun getAllBrands() = apiServices.getAllBrands()
    override suspend fun getAllProductList(searchProduct: SearchProductRequest) =
        apiServices.getAllProductList(searchProduct)

    override suspend fun getAllProductDetails(productId: Int) =
        apiServices.getAllProductDetails(productId)

    override suspend fun register(request: UserRegisterRequest) =
        apiServices.register(request)

    override suspend fun editProfile(request: UserRegisterRequest): Response<BaseWrapper<EditProfileResponse>> =
        apiServices.editProfile(request)

    override suspend fun deleteAccount(): Response<BaseWrapper<Boolean>> =
        apiServices.deleteAccount()

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Response<BaseWrapper<Boolean>> = apiServices.changePassword(currentPassword, newPassword)

    override suspend fun login(userLoginRequest: UserLoginRequest) =
        apiServices.login(userLoginRequest.Phone, userLoginRequest.Password)

    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) =
        apiServices.verifyOtp(verifyOtpRequest)

    override suspend fun resendOtp(userId: Int) =
        apiServices.resendOtp(userId)

    override suspend fun getAllAddresses() =
        apiServices.getAllAddresses()

    override suspend fun addUserAddress(userAddress: UserAddress) =
        apiServices.addUserAddress(userAddress)

    override suspend fun updateUserAddress(userAddress: UserAddress) =
        apiServices.updateUserAddress(userAddress)

    override suspend fun getAllAreas() = apiServices.getAllAreas()

    override suspend fun loadCheckout(checkout: Checkout) =
        apiServices.loadCheckout(checkout)

    override suspend fun loadCheckout(
        request: LoadCheckoutRequest
    ): Response<BaseWrapper<CheckoutModel>> = apiServices.loadCheckout(body = request)

    override suspend fun createOrder(
        request: LoadCheckoutRequest
    ): Response<BaseWrapper<CreateOrderModel>> =
        apiServices.createOrder(body = request)

    override suspend fun getOrdersHistory() = apiServices.getOrdersHistory()

    override suspend fun getAllBazars(): Response<BaseWrapper<List<BazaarModel>>> =
        apiServices.getAllBazars()
}