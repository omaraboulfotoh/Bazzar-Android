package com.android.network.states

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class Result<T>(
    var data: T? = null,
    var message: String? = null,
    val hasMoreData: Boolean? = null,
    var errors: Map<String, String>? = null,
) : IResult<T> {
    class Success<T>(data: T, hasMoreData: Boolean? = null) :
        Result<T>(data, hasMoreData = hasMoreData)

    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(data: T? = null, message: String?) : Result<T>(data, message)
}