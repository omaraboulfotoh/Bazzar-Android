package com.bazzar.android.presentation.composables.permissionRequester

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

@Composable
fun LocationPermissionRequester(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    requestPreciseLocation: Boolean = true
) {
    val permissionRequester = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it.containsValue(false)) {
            onPermissionDenied.invoke()
        } else {
            onPermissionGranted.invoke()
        }
    }

    SideEffect {
        // Prepare permissions list
        val permissions = buildList {
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (requestPreciseLocation) add(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

        permissionRequester.launch(
            permissions.toTypedArray()
        )
    }
}