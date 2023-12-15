package com.acasloa946.blackjack.data

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acasloa946.blackjack.routes.Routes
import com.acasloa946.blackjack.userInterface.Pantalla1vs1
import com.acasloa946.blackjack.userInterface.PantallaModo
import com.acasloa946.blackjack.ui.theme.BlackJackTheme
import com.acasloa946.blackjack.userInterface.HGViewmodel
import com.acasloa946.blackjack.userInterface.Pantalla1vsIA
import com.acasloa946.blackjack.userInterface.PvsIAViewModel
import com.acasloa946.blackjack.userInterface.ScreenHG
import com.acasloa946.blackjack.userInterface.ViewModel1vs1


class MainActivity : ComponentActivity() {

    private val viewModel1vs1 : ViewModel1vs1 by viewModels()
    private val viewModel1vsIA : PvsIAViewModel by viewModels()
    private val HGViewmodel : HGViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlackJackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val NavController = rememberNavController()
                    NavHost(NavController, startDestination = Routes.Pantallamodo.route) {
                        composable(Routes.Pantallamodo.route) { PantallaModo(NavController) }
                        composable(Routes.Pantalla1vs1.route) { Pantalla1vs1(NavController,viewModel1vs1) }
                        composable(Routes.Pantalla1vsIA.route) { Pantalla1vsIA(NavController,viewModel1vsIA) }
                        composable(Routes.PantallaHC.route) { ScreenHG(NavController,HGViewmodel) }

                    }
                }
            }
        }
    }
}

