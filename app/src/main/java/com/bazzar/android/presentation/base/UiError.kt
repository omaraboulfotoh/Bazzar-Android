package com.bazzar.android.presentation.base

import androidx.annotation.StringRes
import com.bazzar.android.R
import com.bazzar.android.common.catchErrorMessage

sealed class UiError {

    data class Dialog(
        @StringRes val title: Int = R.string.error,
        @StringRes val bodyStrRes: Int? = null,
        val body: String? = null,
        @StringRes val button: Int = R.string.got_it
    ) : UiError()

    object Na : UiError()

    companion object {

        fun Throwable.toDialogUiError(): Dialog {
            val (errorStringRes, errorString) = catchErrorMessage()
            return Dialog(body = errorString, bodyStrRes = errorStringRes)
        }
    }
}

sealed class UiLoading {

    object Blocking : UiLoading()

    object Na : UiLoading()
}
