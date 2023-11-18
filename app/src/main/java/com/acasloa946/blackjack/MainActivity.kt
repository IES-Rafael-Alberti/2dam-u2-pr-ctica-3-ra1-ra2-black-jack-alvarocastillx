package com.acasloa946.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.acasloa946.blackjack.routes.Routes
import com.acasloa946.blackjack.screens.Pantalla1vs1
import com.acasloa946.blackjack.screens.PantallaModo
import com.acasloa946.blackjack.ui.theme.BlackJackTheme

class MainActivity : ComponentActivity() {
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
                        composable(Routes.Pantalla1vs1.route) { Pantalla1vs1(NavController) }
                }
                }
            }
        }
    }
}

