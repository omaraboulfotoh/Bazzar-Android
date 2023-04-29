package com.android.network.domain.usecases

import com.android.model.home.*
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.network.domain.repos.HomeRepo
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepo: HomeRepo) {

    suspend fun getHome() = homeRepo.getHome()
    suspend fun getAllCategories() = homeRepo.getAllCategories()
    suspend fun getAllBrands() = homeRepo.getAllBrands()
    suspend fun getAllProductList(searchProduct: SearchProductRequest) =
        homeRepo.getAllProductList(searchProduct)

    suspend fun getAllProductDetails(productId: Int) = homeRepo.getAllProductDetails(productId)
    suspend fun register(request: UserRegisterRequest) = homeRepo.register(request)
    suspend fun login(userLoginRequest: UserLoginRequest) = homeRepo.login(userLoginRequest)
    suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest) = homeRepo.verifyOtp(verifyOtpRequest)
    suspend fun addUserAddress(userAddress: UserAddress) = homeRepo.addUserAddress(userAddress)
    suspend fun updateUserAddress(userAddress: UserAddress) =
        homeRepo.updateUserAddress(userAddress)
    suspend fun loadCheckout(checkout: Checkout) = homeRepo.loadCheckout(checkout)
}