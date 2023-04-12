package com.android.network.domain.usecases

import com.android.network.domain.repos.HomeRepo
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val movieRepo: HomeRepo) {

    suspend fun getHome() = movieRepo.getHome()
}