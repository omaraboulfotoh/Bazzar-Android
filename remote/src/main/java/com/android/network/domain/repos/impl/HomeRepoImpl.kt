package com.android.network.domain.repos.impl

import android.util.Log
import androidx.annotation.WorkerThread
import com.android.model.home.*
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
            Log.e("NetworkError", throwable.localizedMessage)
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
            Log.e("NetworkError", throwable.localizedMessage)
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
            Log.e("NetworkError", throwable.localizedMessage)
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
                Log.e("NetworkError", throwable.localizedMessage)
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
                    emit(Result.Success(it.body()?.data ?: ProductDetail()))
                } else
                    Result.Error(
                        ProductDetail(), "error will be handled"
                    )
            }
        } catch (throwable: Throwable) {
            Log.e("NetworkError", throwable.localizedMessage)
            emit(
                Result.Error(
                    ProductDetail(),
                    throwable.message
                )
            )
        }
    }.onStart { emit(Result.Loading()) }.flowOn(Dispatchers.IO)


}