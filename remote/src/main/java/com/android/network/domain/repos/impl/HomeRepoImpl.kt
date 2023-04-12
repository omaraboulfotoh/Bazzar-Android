package com.android.network.domain.repos.impl

import androidx.annotation.WorkerThread
import com.android.model.home.HomeResponse
import com.android.model.responses.base.MoviesListResponse
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


}