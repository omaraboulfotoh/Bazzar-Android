package com.bazzar.android.presentation.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.theme.AbsComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var globalState: IGlobalState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbsComposeTheme {
                val navController = rememberNavController()
                App(globalState)
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navController,
                    startRoute = NavGraphs.root.startRoute
                )
            }
        }
    }
}
