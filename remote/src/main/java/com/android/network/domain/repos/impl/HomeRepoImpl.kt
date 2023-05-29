package com.android.network.domain.repos.impl

import androidx.annotation.WorkerThread
import com.android.model.home.Area
import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.CheckoutModel
import com.android.model.home.CreateOrderModel
import com.android.model.home.EditProfileResponse
import com.android.model.home.HomeResponse
import com.android.model.home.OrderHistory
import com.android.model.home.Product
import com.android.model.home.SortFilter
import com.android.model.home.UserAddress
import com.android.model.home.UserData
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
import com.android.network.datasource.HomeRemoteDataSource
import com.android.network.domain.repos.HomeRepo
import com.android.network.states.Result
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import java.io.IOException
import javax.inject.Inject


@WorkerThread
class HomeRepoImpl @Inject constructor(var homeRemoteDataSource: HomeRemoteDataSource) : HomeRepo {
    override suspend fun getHome() = flow {
        try {
            homeRemoteDataSource.getHome().let {
                if (it.isSuccessful) {
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: HomeResponse(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else Result.Error(
                    HomeResponse(), handleErrorIn400(it.errorBody().toString())
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
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: emptyList(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else Result.Error(
                    listOf<Category>(), handleErrorIn400(it.errorBody().toString())
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
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: emptyList(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else Result.Error(
                    listOf<Brand>(), handleErrorIn400(it.errorBody().toString())
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
                                data = it.body()?.data ?: emptyList(),
                                hasMoreData = it.body()?.hasMoreData,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else Result.Error(
                        listOf<Product>(), handleErrorIn400(it.errorBody().toString())
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
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: Product(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else Result.Error(
                    Product(), handleErrorIn400(it.errorBody().toString())
                )
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(
                    Product(), throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun loadFiltersAndSorting(queryMap: Map<String, String>) =
        flow {
            try {
                homeRemoteDataSource.loadFiltersAndSorting(queryMap).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: SortFilter(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else Result.Error(SortFilter(), handleErrorIn400(it.errorBody().toString()))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(SortFilter(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun register(request: UserRegisterRequest) = flow {
        try {
            homeRemoteDataSource.register(request).let {
                if (it.isSuccessful) {
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: UserData(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else Result.Error(
                    UserData(), handleErrorIn400(it.errorBody().toString())
                )
            }
        } catch (throwable: Throwable) {
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
                        emit(
                            Result.Success(
                                data = it.body()?.data,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else Result.Error(
                        null, handleErrorIn400(it.errorBody().toString())
                    )
                }
            } catch (throwable: Throwable) {
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
                            data = it.body()?.data ?: EditProfileResponse(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else Result.Error(
                        EditProfileResponse(),
                        handleErrorIn400(it.errorBody().toString())
                    )
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(EditProfileResponse(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteAccount(): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteAccount().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data == true,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else Result.Error(false, handleErrorIn400(it.errorBody().toString()))
                }
            } catch (throwable: Throwable) {
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
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: false,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else Result.Error(false, handleErrorIn400(it.errorBody().toString()))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun login(userLoginRequest: UserLoginRequest): Flow<Result<UserData>> = flow {
        try {
            homeRemoteDataSource.login(userLoginRequest).let {
                if (it.isSuccessful) {
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: UserData(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else Result.Error(UserData(), handleErrorIn400(it.errorBody().toString()))
            }
        } catch (throwable: Throwable) {
            emit(
                Result.Error(UserData(), throwable.message)
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun loginGuest(guestLoginRequest: GuestLoginRequest): Flow<Result<UserData>> =
        flow {
            try {
                homeRemoteDataSource.loginGuest(guestLoginRequest).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: UserData(),
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else Result.Error(
                        UserData(), handleErrorIn400(it.errorBody().toString())
                    )
                }
            } catch (throwable: Throwable) {
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
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: UserData(),
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else Result.Error(
                        UserData(), handleErrorIn400(it.errorBody().toString())
                    )
                }
            } catch (throwable: Throwable) {
                emit(
                    Result.Error(
                        UserData(), throwable.message
                    )
                )
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun resendOtp(userId: Int): Flow<Result<Boolean>> = flow {
        try {
            homeRemoteDataSource.resendOtp(userId).let {
                if (it.isSuccessful) {
                    emit(
                        Result.Success(
                            data = it.body()?.data ?: false,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                } else {
                    Result.Error(false, handleErrorIn400(it.errorBody().toString()))
                }
            }
        } catch (throwable: Throwable) {

            emit(Result.Error(false, throwable.message))
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllAddresses(): Flow<Result<List<UserAddress>>> = flow {
        try {
            homeRemoteDataSource.getAllAddresses().let {
                if (it.isSuccessful) emit(
                    Result.Success(
                        data = it.body()?.data ?: listOf(),
                        message = it.body()?.message,
                        code = it.body()?.code
                    )
                )
                else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
            }
        } catch (throwable: Throwable) {

            emit(Result.Error(listOf(), throwable.message))
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>> =
        flow {
            try {
                homeRemoteDataSource.addUserAddress(userAddress).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: UserAddress(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(
                        Result.Error(
                            UserAddress(),
                            handleErrorIn400(it.errorBody().toString())
                        )
                    )
                }
            } catch (throwable: Throwable) {

                emit(Result.Error(UserAddress(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun updateUserAddress(userAddress: UserAddress): Flow<Result<UserAddress>> =
        flow {
            try {
                homeRemoteDataSource.updateUserAddress(userAddress).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: UserAddress(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(
                        Result.Error(
                            UserAddress(),
                            handleErrorIn400(it.errorBody().toString())
                        )
                    )
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(UserAddress(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllAreas(): Flow<Result<List<Area>>> = flow {
        try {
            homeRemoteDataSource.getAllAreas().let {
                if (it.isSuccessful) emit(
                    Result.Success(
                        data = it.body()?.data ?: listOf(),
                        message = it.body()?.message,
                        code = it.body()?.code
                    )
                )
                else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
            }
        } catch (throwable: Throwable) {
            emit(Result.Error(listOf(), throwable.message))
        }
    }.onStart { emit(Result.Loading(listOf())) }.flowOn(Dispatchers.IO)

    override suspend fun loadCheckout(request: LoadCheckoutRequest): Flow<Result<CheckoutModel>> =
        flow {
            try {
                homeRemoteDataSource.loadCheckout(request).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: CheckoutModel(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(
                        Result.Error(
                            CheckoutModel(),
                            handleErrorIn400(it.errorBody().toString())
                        )
                    )
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(CheckoutModel(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getOrdersHistory(lastDaysCount: Int): Flow<Result<List<OrderHistory>>> =
        flow {
            try {
                homeRemoteDataSource.getOrdersHistory(lastDaysCount).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: listOf(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun createOrder(request: LoadCheckoutRequest): Flow<Result<CreateOrderModel>> =
        flow {
            try {
                homeRemoteDataSource.createOrder(request).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: CreateOrderModel(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(
                        Result.Error(
                            CreateOrderModel(),
                            handleErrorIn400(it.errorBody().toString())
                        )
                    )
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(CreateOrderModel(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAllBazars(): Flow<Result<List<BazaarModel>>> =
        flow {
            try {
                homeRemoteDataSource.getAllBazars().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: listOf(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getBazaarDetails(bazaarId: Int): Flow<Result<BazaarDetailsResponse>> =
        flow {
            try {
                homeRemoteDataSource.getBazaarDetails(bazaarId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: BazaarDetailsResponse(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(
                        Result.Error(
                            BazaarDetailsResponse(),
                            handleErrorIn400(it.errorBody().toString())
                        )
                    )
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(BazaarDetailsResponse(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getProductWishList(): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.getProductWishList().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: listOf(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addProductWishList(itemId: Int): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.addProductWishList(itemId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: false,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteProductWishList(itemId: Int) = flow {
        try {
            homeRemoteDataSource.deleteProductWishList(itemId).let {
                if (it.isSuccessful) emit(
                    Result.Success(
                        data = it.body()?.data ?: false,
                        message = it.body()?.message,
                        code = it.body()?.code
                    )
                )
                else emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
            }
        } catch (throwable: Throwable) {
            emit(Result.Error(false, throwable.message))
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getBazaarsWishList(): Flow<Result<List<BazaarModel>>> =
        flow {
            try {
                homeRemoteDataSource.getBazaarsWishList().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: listOf(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addBazaarWishList(marketerId: Int): Flow<Result<Boolean>> = flow {
        try {
            homeRemoteDataSource.addBazaarWishList(marketerId).let {
                if (it.isSuccessful) emit(
                    Result.Success(
                        data = it.body()?.data ?: false,
                        message = it.body()?.message,
                        code = it.body()?.code
                    )
                )
                else emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
            }
        } catch (throwable: Throwable) {
            emit(Result.Error(false, throwable.message))
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteBazaarWishList(marketerId: Int): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteBazaarWishList(marketerId).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: false,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun addToCart(addToCartRequest: AddToCartRequest): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.addToCart(addToCartRequest).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: false,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteFromCart(
        itemDetailId: Int,
        addToWishList: Boolean
    ): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteFromCart(itemDetailId, addToWishList).let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: false,
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun loadCart(): Flow<Result<List<Product>>> =
        flow {
            try {
                homeRemoteDataSource.loadCart().let {
                    if (it.isSuccessful) emit(
                        Result.Success(
                            data = it.body()?.data ?: listOf(),
                            message = it.body()?.message,
                            code = it.body()?.code
                        )
                    )
                    else emit(Result.Error(listOf(), handleErrorIn400(it.errorBody().toString())))
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(listOf(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun clearCart(): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.clearCart().let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: false,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)


    override suspend fun updateCartQuantity(
        itemDetailId: Int,
        qty: Int
    ): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.updateCartQuantity(itemDetailId, qty).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: false,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun getAboutUs(): Flow<Result<String>> =
        flow {
            try {
                homeRemoteDataSource.getAboutUs().let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: "",
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(Result.Error("", handleErrorIn400(it.errorBody().toString())))
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error("", throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun submitContractUs(contactUsRequest: ContactUsRequest): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.submitContractUs(contactUsRequest).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: false,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)


    override suspend fun getOrdersDetails(orderId: Int): Flow<Result<OrderHistory>> =
        flow {
            try {
                homeRemoteDataSource.getOrdersDetails(orderId).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: OrderHistory(),
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(
                            Result.Error(
                                OrderHistory(),
                                handleErrorIn400(it.errorBody().toString())
                            )
                        )
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(OrderHistory(), throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun requestForgetPassword(phone: String): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.requestForgetPassword(phone).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: false,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)

    override suspend fun deleteAddress(userAddressId: Int): Flow<Result<Boolean>> =
        flow {
            try {
                homeRemoteDataSource.deleteAddress(userAddressId).let {
                    if (it.isSuccessful) {
                        emit(
                            Result.Success(
                                data = it.body()?.data ?: false,
                                message = it.body()?.message,
                                code = it.body()?.code
                            )
                        )
                    } else {
                        emit(Result.Error(false, handleErrorIn400(it.errorBody().toString())))
                    }
                }
            } catch (throwable: Throwable) {
                emit(Result.Error(false, throwable.message))
            }
        }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)
}

fun handleErrorIn400(errorBody: String): String {
    val gson = GsonBuilder().create()
    return try {
        gson.fromJson(errorBody, BaseWrapper::class.java).message.orEmpty()

    } catch (e: IOException) {
        // handle failure to read error
        "Something went wrong"
    }
}