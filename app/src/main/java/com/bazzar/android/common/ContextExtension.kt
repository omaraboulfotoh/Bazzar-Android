package com.bazzar.android.common

import android.content.Context
import android.content.Intent
import com.bazzar.android.presentation.app.MainActivity

fun Context.startNewTaskMainActivity() {
    Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra("SkipSplash",true)
        startActivity(this)
    }
}