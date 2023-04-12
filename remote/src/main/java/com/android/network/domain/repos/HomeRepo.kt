package com.android.network.domain.repos

import com.android.model.home.HomeResponse
import com.android.model.responses.base.MoviesListResponse
import com.android.network.states.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun getHome(): Flow<Result<HomeResponse>>
}