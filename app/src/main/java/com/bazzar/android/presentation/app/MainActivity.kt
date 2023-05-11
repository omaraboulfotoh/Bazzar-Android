package com.bazzar.android.presentation.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.android.local.SharedPrefersManager
import com.bazzar.android.presentation.theme.BazzarComposeTheme
import com.bazzar.android.utils.ContextWrapper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var globalState: IGlobalState

    @Inject
    lateinit var prefersManager: SharedPrefersManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BazzarComposeTheme {
                App(globalState)
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val locale = Locale(prefersManager.getAppLanguage())
        val context = ContextWrapper.wrap(newBase, locale)
        super.attachBaseContext(context)
    }

    companion object {
        fun restartApp(activity: AppCompatActivity) {
            activity.finish()
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}
