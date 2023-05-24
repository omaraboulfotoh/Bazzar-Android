package com.android.network.states


sealed class Result<T>(
    var data: T? = null,
    var message: String? = null,
    val hasMoreData: Boolean? = null,
    val code: Int? = null,
    var errors: Map<String, String>? = null,
) : IResult<T> {
    class Success<T>(
        data: T,
        hasMoreData: Boolean? = null,
        message: String? = null,
        code: Int? = null
    ) :
        Result<T>(data, hasMoreData = hasMoreData, message = message, code = code)

    class Loading<T>(data: T? = null) : Result<T>(data)
    class Error<T>(data: T? = null, message: String?, code: Int? = null) :
        Result<T>(data, message, code = code)
}