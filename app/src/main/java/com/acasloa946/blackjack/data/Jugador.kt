package com.acasloa946.blackjack.data

import androidx.lifecycle.MutableLiveData

class Jugador(mano: MutableLiveData<MutableList<Carta>>, fichas: Float?, nombre: String) {
    var Mano = mano
    var Fichas = fichas
    var Nombre = nombre

}