package com.android.network.domain.usecases

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
import com.android.network.domain.repos.HomeRepo
import com.android.network.states.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepo: HomeRepo) {

    suspend fun getHome() = homeRepo.getHome()
    suspend fun getAllCategories() = homeRepo.getAllCategories()
    suspend fun getAllBrands() = homeRepo.getAllBrands()
    suspend fun getAllProductList(searchProduct: SearchProductRequest) =
        homeRepo.getAllProductList(searchProduct)

    suspend fun getAllProductDetails(productId: Int) =
        homeRepo.getAllProductDetails(productId)

    suspend fun loadFiltersAndSorting(queryMap: Map<String, String>) =
        homeRepo.loadFiltersAndSorting(queryMap)

    suspend fun register(request: UserRegisterRequest) = homeRepo.register(request)
    suspend fun updateFcmToken(token: String) = homeRepo.updateFcmToken(token)
    suspend fun editProfile(request: UserRegisterRequest) = homeRepo.editProfile(request)
    suspend fun deleteAccount() = homeRepo.deleteAccount()
    suspend fun changePassword(currentPassword: String, newPassword: String) =
        homeRepo.changePassword(currentPassword = currentPassword, newPassword = newPassword)

    suspend fun login(userLoginRequest: UserLoginRequest) =
        homeRepo.login(userLoginRequest)

    suspend fun loginGuest(guestLoginRequest: GuestLoginRequest) =
        homeRepo.loginGuest(guestLoginRequest)

    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) =
        homeRepo.verifyOtp(verifyOtpRequest)

    suspend fun resendOtp(userId: Int) =
        homeRepo.resendOtp(userId)

    suspend fun getAllAddresses() =
        homeRepo.getAllAddresses()

    suspend fun addUserAddress(userAddress: UserAddress) =
        homeRepo.addUserAddress(userAddress)

    suspend fun getAllAreas() = homeRepo.getAllAreas()
    suspend fun updateUserAddress(userAddress: UserAddress) =
        homeRepo.updateUserAddress(userAddress)

    suspend fun getOrdersHistory(lastDaysCount: Int) = homeRepo.getOrdersHistory(lastDaysCount)
    suspend fun loadCheckout(request: LoadCheckoutRequest) = homeRepo.loadCheckout(request)

    suspend fun createOrder(request: LoadCheckoutRequest) =
        homeRepo.createOrder(request)

    suspend fun getAllBazars() = homeRepo.getAllBazars()
    suspend fun getBazaarDetails(bazaarId: Int) = homeRepo.getBazaarDetails(bazaarId)
    suspend fun getProductWishList() = homeRepo.getProductWishList()
    suspend fun addProductWishList(itemId: Int) = homeRepo.addProductWishList(itemId)
    suspend fun deleteProductWishList(itemId: Int) = homeRepo.deleteProductWishList(itemId)
    suspend fun getBazaarsWishList() = homeRepo.getBazaarsWishList()
    suspend fun addBazaarWishList(marketerId: Int) = homeRepo.addBazaarWishList(marketerId)
    suspend fun deleteBazaarWishList(marketerId: Int) = homeRepo.deleteBazaarWishList(marketerId)
    suspend fun addToCart(addToCartRequest: AddToCartRequest) = homeRepo.addToCart(addToCartRequest)
    suspend fun deleteFromCart(itemDetailId: Int, addToWishList: Boolean = false) =
        homeRepo.deleteFromCart(itemDetailId, addToWishList)

    suspend fun loadCart() = homeRepo.loadCart()
    suspend fun updateCartQuantity(itemDetailId: Int, qty: Int) =
        homeRepo.updateCartQuantity(itemDetailId, qty)

    suspend fun clearCart() = homeRepo.clearCart()
    suspend fun getAboutUs() = homeRepo.getAboutUs()
    suspend fun submitContractUs(contactUsRequest: ContactUsRequest) =
        homeRepo.submitContractUs(contactUsRequest)

    suspend fun getOrdersDetails(orderId: Int) =
        homeRepo.getOrdersDetails(orderId)

    suspend fun requestForgetPassword(phone: String) = homeRepo.requestForgetPassword(phone)
    suspend fun deleteAddress(userAddressId: Int) = homeRepo.deleteAddress(userAddressId)

}