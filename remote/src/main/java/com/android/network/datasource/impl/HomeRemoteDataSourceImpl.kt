package com.android.network.datasource.impl

import com.android.model.home.*
import com.android.model.request.AddToCartRequest
import com.android.model.request.ChangePasswordRequest
import com.android.model.request.ContactUsRequest
import com.android.model.request.GuestLoginRequest
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
    override suspend fun getAllProductList(searchProductRequest: SearchProductRequest) =
        apiServices.getAllProductList(searchProductRequest)

    override suspend fun getAllProductDetails(productId: Int) =
        apiServices.getAllProductDetails(productId)

    override suspend fun loadFiltersAndSorting(queryMap: Map<String, String>) =
        apiServices.loadFiltersAndSorting(queryMap)

    override suspend fun register(request: UserRegisterRequest) =
        apiServices.register(request)

    override suspend fun updateFcmToken(fcmToken: String) =
        apiServices.updateFcmToken(fcmToken)

    override suspend fun editProfile(request: UserRegisterRequest) =
        apiServices.editProfile(request)

    override suspend fun deleteAccount() =
        apiServices.deleteAccount()

    override suspend fun changePassword(currentPassword: String, newPassword: String) =
        apiServices.changePassword(ChangePasswordRequest(currentPassword, newPassword))

    override suspend fun login(userLoginRequest: UserLoginRequest) =
        apiServices.login(userLoginRequest)

    override suspend fun loginGuest(guestLoginRequest: GuestLoginRequest) =
        apiServices.loginGuest(guestLoginRequest)

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

    override suspend fun loadCheckout(request: LoadCheckoutRequest) =
        apiServices.loadCheckout(body = request)

    override suspend fun createOrder(request: LoadCheckoutRequest) =
        apiServices.createOrder(body = request)

    override suspend fun getOrdersHistory(lastDaysCount: Int) =
        apiServices.getOrdersHistory(lastDaysCount)

    override suspend fun getAllBazars() =
        apiServices.getAllBazars()

    override suspend fun getBazaarDetails(bazaarId: Int) =
        apiServices.getBazaarDetails(bazaarId)

    override suspend fun getProductWishList() =
        apiServices.getProductWishList()

    override suspend fun addProductWishList(itemId: Int) =
        apiServices.addProductWishList(itemId)

    override suspend fun deleteProductWishList(itemId: Int) =
        apiServices.deleteProductWishList(itemId)

    override suspend fun getBazaarsWishList() =
        apiServices.getBazaarsWishList()

    override suspend fun addBazaarWishList(marketerId: Int) =
        apiServices.addBazaarWishList(marketerId)

    override suspend fun deleteBazaarWishList(marketerId: Int) =
        apiServices.deleteBazaarWishList(marketerId)

    override suspend fun addToCart(addToCartRequest: AddToCartRequest) =
        apiServices.addToCart(addToCartRequest)

    override suspend fun deleteFromCart(itemDetailId: Int, addToWishList: Boolean) =
        apiServices.deleteFromCart(itemDetailId, addToWishList)

    override suspend fun clearCart() =
        apiServices.clearCart()

    override suspend fun loadCart() =
        apiServices.loadCart()

    override suspend fun updateCartQuantity(itemDetailId: Int, qty: Int) =
        apiServices.updateCartQuantity(itemDetailId, qty)

    override suspend fun getAboutUs() = apiServices.getAboutUs()

    override suspend fun submitContractUs(contactUsRequest: ContactUsRequest) =
        apiServices.submitContractUs(contactUsRequest)

    override suspend fun getOrdersDetails(orderId: Int) = apiServices.getOrdersDetails(orderId)
}