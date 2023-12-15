package com.acasloa946.blackjack.routes

sealed class Routes(var route:String) {
    object Pantallamodo : Routes("Pantallamodo")
    object Pantalla1vs1 : Routes("Pantalla1vs1")
    object Pantalla1vsIA : Routes("Pantalla1vsIA")
    object PantallaHC : Routes("ScreenHG")

}