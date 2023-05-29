package com.bazzar.android.presentation.app

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

data class ConfirmationDialogParams(
    val title: Int,
    val description: Int,
    val positiveButtonTitle: Int,
    val onPositive: () -> Unit,
    val negativeButtonTitle: Int,
    val onNegative: (() -> Unit)? = null,
)

interface IGlobalState {
    val loadingState: State<Boolean>
    val errorState: State<String?>
    val errorStateRes: State<Int?>
    val successState: State<String?>
    val successStateRes: State<Int?>
    val confirmationState: State<ConfirmationDialogParams?>
    val appLoaded: State<Boolean>

    fun idle()
    fun loading(show: Boolean)
    fun error(msg: String, hideLoading: Boolean = true)
    fun error(msg: Int, hideLoading: Boolean = true)
    fun error(msgs: List<String>, hideLoading: Boolean = true)
    fun success(msg: String, hideLoading: Boolean = true)
    fun success(msg: Int, hideLoading: Boolean = true)
    fun confirmationDialog(params: ConfirmationDialogParams, hideLoading: Boolean = true)
    fun appLoaded()
}

class GlobalState : IGlobalState {
    override val loadingState = mutableStateOf(false)
    override val errorState = mutableStateOf<String?>(null)
    override val errorStateRes = mutableStateOf<Int?>(null)
    override val successState = mutableStateOf<String?>(null)
    override val successStateRes = mutableStateOf<Int?>(null)
    override val confirmationState = mutableStateOf<ConfirmationDialogParams?>(null)
    override val appLoaded = mutableStateOf(false)

    override fun idle() {
        loadingState.value = false
        errorState.value = null
        errorStateRes.value = null
        successState.value = null
        successStateRes.value = null
        confirmationState.value = null
    }

    override fun loading(show: Boolean) {
        if (show) {
            errorState.value = null
            errorStateRes.value = null
            confirmationState.value = null
            successState.value = null
            successStateRes.value = null
        }
        loadingState.value = show
    }

    override fun error(msg: String, hideLoading: Boolean) {
        if (hideLoading) loadingState.value = false
        confirmationState.value = null
        successState.value = null
        successStateRes.value = null
        errorState.value = msg
        errorStateRes.value = null
    }

    override fun error(msg: Int, hideLoading: Boolean) {
        if (hideLoading) loadingState.value = false
        confirmationState.value = null
        successState.value = null
        successStateRes.value = null
        errorState.value = null
        errorStateRes.value  = msg
    }

    override fun error(msgs: List<String>, hideLoading: Boolean) {
        if (hideLoading) loadingState.value = false
        confirmationState.value = null
        successState.value = null
        successStateRes.value = null
        errorState.value = msgs.joinToString("\n")
        errorStateRes.value = null
    }

    override fun success(msg: String, hideLoading: Boolean) {
        if (hideLoading) loadingState.value = false
        confirmationState.value = null
        errorState.value = null
        errorStateRes.value = null
        successState.value = msg
        successStateRes.value = null
    }
    override fun success(msg: Int, hideLoading: Boolean) {
        if (hideLoading) loadingState.value = false
        confirmationState.value = null
        errorState.value = null
        errorStateRes.value = null
        successState.value = null
        successStateRes.value = msg
    }

    override fun confirmationDialog(params: ConfirmationDialogParams, hideLoading: Boolean) {
        if (hideLoading) loadingState.value = false
        errorState.value = null
        errorStateRes.value = null
        successState.value = null
        successStateRes.value = null
        confirmationState.value = params
    }

    override fun appLoaded() {
        appLoaded.value = true
    }
}
