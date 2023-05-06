package com.android.network.domain.repos.impl

import android.util.Log
import androidx.annotation.WorkerThread
import com.android.model.home.Area
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Checkout
import com.android.model.home.CheckoutModel
import com.android.model.home.HomeResponse
import com.android.model.home.Product
import com.android.model.home.UserAddress
import com.android.model.home.UserData
import com.android.model.home.OrderHistory
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.network.datasource.HomeRemoteDataSource
import com.android.network.domain.repos.HomeRepo
import com.android.network.states.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@WorkerThread
class HomeRepoImpl @Inject constructor(var homeRemoteDataSource: HomeRemoteDataSource) : HomeRepo {
    override suspend fun getHome() = flow {
        try {
            homeRemoteDataSource.getHome().let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: HomeResponse()))
                } else
                    Result.Error(
                        HomeResponse(), "error will be handled"
                    )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    HomeResponse(),
                    throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllCategories() = flow {
        try {
            homeRemoteDataSource.getAllCategories().let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: emptyList()))
                } else
                    Result.Error(
                        listOf<Category>(), "error will be handled"
                    )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    listOf<Category>(),
                    throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllBrands(): Flow<Result<List<Brand>>> = flow {
        try {
            homeRemoteDataSource.getAllBrands().let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: emptyList()))
                } else
                    Result.Error(
                        listOf<Brand>(), "error will be handled"
                    )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    listOf<Brand>(),
                    throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllProductList(searchProduct: SearchProductRequest): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.getAllProductList(searchProduct).let {
                    if (it.isSuccessful) {
                        emit(Result.Success(it.body()?.data ?: emptyList()))
                    } else
                        Result.Error(
                            listOf<Product>(), "error will be handled"
                        )
                }
            } catch (throwable: Throwable) {
                emit(
                    Result.Error(
                        listOf<Product>(),
                        throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllProductDetails(productId: Int) = flow {
        try {
            homeRemoteDataSource.getAllProductDetails(productId).let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: Product()))
                } else
                    Result.Error(
                        Product(), "error will be handled"
                    )
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "getAllProductDetails: ", throwable)
            emit(
                Result.Error(
                    Product(),
                    throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun register(request: UserRegisterRequest) = flow {
        try {
            homeRemoteDataSource.register(request).let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: UserData()))
                } else
                    Result.Error(
                        UserData(), "error will be handled"
                    )
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "RegisterError: ", throwable)
            emit(
                Result.Error(
                    UserData(),
                    throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)


    override suspend fun login(userLoginRequest: UserLoginRequest): Flow<Result<UserData>> =
        flow {
            try {
                homeRemoteDataSource.login(userLoginRequest).let {
                    if (it.isSuccessful) {
                        emit(Result.Success(it.body()?.data ?: UserData()))
                    } else
                        Result.Error(
                            UserData(), "error will be handled"
                        )
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "LoginError: ", throwable)
                emit(
                    Result.Error(
                        UserData(),
                        throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)


    override suspend fun verifyOtp(verifyOtpRequest: VerifyOtpRequest): Flow<Result<UserData>> =
        flow {
            try {
                homeRemoteDataSource.verifyOtp(verifyOtpRequest).let {
                    if (it.isSuccessful) {
                        emit(Result.Success(it.body()?.data ?: UserData()))
                    } else
                        Result.Error(
                            UserData(), "error will be handled"
                        )
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "verifyOtpError: ", throwable)
                emit(
                    Result.Error(
                        UserData(),
                        throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun resendOtp(userId: Int): Flow<Result<String>> =
        flow {
            try {
                homeRemoteDataSource.resendOtp(userId).let {
                    if (it.isSuccessful) {
                        emit(Result.Success(""))
                    } else {
                        Result.Error("", "error will be handled")
                    }
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "verifyOtpError: ", throwable)
                emit(Result.Error("", throwable.message))
            }
        }.onStart { emit(Result.Loading("")) }.flowOn(Dispatchers.IO)

    override suspend fun getAllAddresses(): Flow<Result<List<UserAddress>>> =
        flow {
            try {
                homeRemoteDataSource.getAllAddresses().let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: listOf()))
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllAddresses: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>> =
        flow {
            try {
                homeRemoteDataSource.addUserAddress(userAddress).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: UserAddress()))
                    else emit(Result.Error(UserAddress(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "addUserAddress: ", throwable)
                emit(Result.Error(UserAddress(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun updateUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>> =
        flow {
            try {
                homeRemoteDataSource.updateUserAddress(userAddress).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: UserAddress()))
                    else emit(Result.Error(UserAddress(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "updateUserAddress: ", throwable)
                emit(Result.Error(UserAddress(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllAreas(arabic: Boolean): Flow<Result<List<Area>>> =
        flow {
            try {
                homeRemoteDataSource.getAllAreas(arabic).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: listOf()))
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllAreas: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading(listOf())) }.flowOn(Dispatchers.IO)

    override suspend fun loadCheckout(checkout: Checkout): Flow<Result<Any>> {
        TODO("Not yet implemented")
    }

    override suspend fun loadCheckout(
        arabic: Boolean,
        request: LoadCheckoutRequest
    ): Flow<Result<CheckoutModel>> =
        flow {
            try {
                homeRemoteDataSource.loadCheckout(arabic, request).let {
                    if (it.isSuccessful) emit(
                        Result.Success(it.body()?.data ?: CheckoutModel())
                    )
                    else emit(Result.Error(CheckoutModel(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getOrdersHistory: ", throwable)
                emit(Result.Error(CheckoutModel(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getOrdersHistory(arabic: Boolean): Flow<Result<List<OrderHistory>>> =
        flow {
            try {
                homeRemoteDataSource.getOrdersHistory(arabic).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: listOf()))
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getOrdersHistory: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)
}