package com.android.network.domain.repos.impl

import android.util.Log
import androidx.annotation.WorkerThread
import com.android.model.home.Area
import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Checkout
import com.android.model.home.CheckoutModel
import com.android.model.home.CreateOrderModel
import com.android.model.home.EditProfileResponse
import com.android.model.home.HomeResponse
import com.android.model.home.Product
import com.android.model.home.UserAddress
import com.android.model.home.UserData
import com.android.model.home.OrderHistory
import com.android.model.request.AddToCartRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.model.request.SearchProductRequest
import com.android.model.request.UserLoginRequest
import com.android.model.request.UserRegisterRequest
import com.android.model.request.VerifyOtpRequest
import com.android.model.responses.base.BazaarDetailsResponse
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
                } else Result.Error(
                    HomeResponse(), "error will be handled"
                )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    HomeResponse(), throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllCategories() = flow {
        try {
            homeRemoteDataSource.getAllCategories().let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: emptyList()))
                } else Result.Error(
                    listOf<Category>(), "error will be handled"
                )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    listOf<Category>(), throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllBrands(): Flow<Result<List<Brand>>> = flow {
        try {
            homeRemoteDataSource.getAllBrands().let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: emptyList()))
                } else Result.Error(
                    listOf<Brand>(), "error will be handled"
                )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    listOf<Brand>(), throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllProductList(searchProduct: SearchProductRequest): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.getAllProductList(searchProduct).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                it.body()?.data ?: emptyList(),
                                hasMoreData = it.body()?.hasMoreData
                            )
                        )
                    } else Result.Error(
                        listOf<Product>(), "error will be handled"
                    )
                }
            } catch (throwable: Throwable) {
                emit(
                    Result.Error(
                        listOf<Product>(), throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllProductDetails(productId: Int) = flow {
        try {
            homeRemoteDataSource.getAllProductDetails(productId).let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: Product()))
                } else Result.Error(
                    Product(), "error will be handled"
                )
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "getAllProductDetails: ", throwable)
            emit(
                Result.Error(
                    Product(), throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun register(request: UserRegisterRequest) = flow {
        try {
            homeRemoteDataSource.register(request).let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: UserData()))
                } else Result.Error(
                    UserData(), "error will be handled"
                )
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "RegisterError: ", throwable)
            emit(
                Result.Error(
                    UserData(), throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun updateFcmToken(fcmToken: String) =
        flow {
            try {
                homeRemoteDataSource.updateFcmToken(fcmToken).let {
                    if (it.isSuccessful) {
                        emit(Result.Success(it.body()?.data))
                    } else Result.Error(
                        null, "error will be handled"
                    )
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "updateFcmToken: ", throwable)
                emit(
                    Result.Error(
                        null, throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun editProfile(request: UserRegisterRequest): Flow<Result<EditProfileResponse>> =
        flow {
            try {
                homeRemoteDataSource.editProfile(request).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: EditProfileResponse()
                        )
                    )
                    else Result.Error(EditProfileResponse(), "error will be handled")
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "editProfile: ", throwable)
                emit(Result.Error(EditProfileResponse(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteAccount(): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteAccount().let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data == true))
                    else Result.Error(false, "error will be handled")
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "deleteAccount: ", throwable)
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.changePassword(currentPassword, newPassword).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: false))
                    else Result.Error(false, "error will be handled")
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "changePassword: ", throwable)
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun login(userLoginRequest: UserLoginRequest): Flow<Result<UserData>> = flow {
        try {
            homeRemoteDataSource.login(userLoginRequest).let {
                if (it.isSuccessful) {
                    emit(Result.Success(it.body()?.data ?: UserData()))
                } else Result.Error(
                    UserData(), "error will be handled"
                )
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "LoginError: ", throwable)
            emit(
                Result.Error(
                    UserData(), throwable.message
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
                    } else Result.Error(
                        UserData(), "error will be handled"
                    )
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "verifyOtpError: ", throwable)
                emit(
                    Result.Error(
                        UserData(), throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun resendOtp(userId: Int): Flow<Result<String>> = flow {
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

    override suspend fun getAllAddresses(): Flow<Result<List<UserAddress>>> = flow {
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

    override suspend fun getAllAreas(): Flow<Result<List<Area>>> = flow {
        try {
            homeRemoteDataSource.getAllAreas().let {
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

    override suspend fun loadCheckout(request: LoadCheckoutRequest): Flow<Result<CheckoutModel>> =
        flow {
            try {
                homeRemoteDataSource.loadCheckout(request).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: CheckoutModel()))
                    else emit(Result.Error(CheckoutModel(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getOrdersHistory: ", throwable)
                emit(Result.Error(CheckoutModel(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getOrdersHistory(): Flow<Result<List<OrderHistory>>> =
        flow {
            try {
                homeRemoteDataSource.getOrdersHistory().let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: listOf()))
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getOrdersHistory: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun createOrder(request: LoadCheckoutRequest): Flow<Result<CreateOrderModel>> =
        flow {
            try {
                homeRemoteDataSource.createOrder(request).let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: CreateOrderModel()))
                    else emit(Result.Error(CreateOrderModel(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getOrdersHistory: ", throwable)
                emit(Result.Error(CreateOrderModel(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllBazars(): Flow<Result<List<BazaarModel>>> =
        flow {
            try {
                homeRemoteDataSource.getAllBazars().let {
                    if (it.isSuccessful) emit(Result.Success(it.body()?.data ?: listOf()))
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getBazaarDetails(bazaarId: Int): Flow<Result<BazaarDetailsResponse>> =
        flow {
            try {
                homeRemoteDataSource.getBazaarDetails(bazaarId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: BazaarDetailsResponse()
                        )
                    )
                    else emit(Result.Error(BazaarDetailsResponse(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(BazaarDetailsResponse(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getProductWishList(): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.getProductWishList().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: listOf()
                        )
                    )
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addProductWishList(itemId: Int): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.addProductWishList(itemId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: false
                        )
                    )
                    else emit(Result.Error(false, "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteProductWishList(itemId: Int) = flow {
        try {
            homeRemoteDataSource.deleteProductWishList(itemId).let {
                if (it.isSuccessful) emit(
                    Result.Success(
                        it.body()?.data ?: false
                    )
                )
                else emit(Result.Error(false, "error will be handled"))
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "getAllBazars: ", throwable)
            emit(Result.Error(false, throwable.message))
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getBazaarsWishList(): Flow<Result<List<BazaarModel>>> =
        flow {
            try {
                homeRemoteDataSource.getBazaarsWishList().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: listOf()
                        )
                    )
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addBazaarWishList(marketerId: Int): Flow<Result<Boolean>> = flow {
        try {
            homeRemoteDataSource.addBazaarWishList(marketerId).let {
                if (it.isSuccessful) emit(
                    Result.Success(
                        it.body()?.data ?: false
                    )
                )
                else emit(Result.Error(false, "error will be handled"))
            }
        } catch (throwable: Throwable) {
            Log.e("Error", "getAllBazars: ", throwable)
            emit(Result.Error(false, throwable.message))
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteBazaarWishList(marketerId: Int): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteBazaarWishList(marketerId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: false
                        )
                    )
                    else emit(Result.Error(false, "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addToCart(addToCartRequest: AddToCartRequest): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.addToCart(addToCartRequest).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: false
                        )
                    )
                    else emit(Result.Error(false, "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteFromCart(itemDetailId: Int): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteFromCart(itemDetailId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: false
                        )
                    )
                    else emit(Result.Error(false, "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun loadCart(): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.loadCart().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: listOf()
                        )
                    )
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun updateCartQuantity(
        itemDetailId: Int,
        qty: Int
    ): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.updateCartQuantity(itemDetailId, qty).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            it.body()?.data ?: listOf()
                        )
                    )
                    else emit(Result.Error(listOf(), "error will be handled"))
                }
            } catch (throwable: Throwable) {
                Log.e("Error", "getAllBazars: ", throwable)
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

}