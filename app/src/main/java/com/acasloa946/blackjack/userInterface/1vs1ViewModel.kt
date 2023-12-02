package com.acasloa946.blackjack.userInterface

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acasloa946.blackjack.data.Baraja
import com.acasloa946.blackjack.data.Carta
import com.acasloa946.blackjack.data.Jugador

class ViewModel1vs1(application: Application) : AndroidViewModel(application) {

    private val baraja = Baraja



    private var _handP1 = MutableLiveData<MutableList<Carta>>()
    var handP1 : LiveData<MutableList<Carta>> = _handP1

    private var _handP2 = MutableLiveData<MutableList<Carta>>()
    var handP2 : LiveData<MutableList<Carta>> = _handP2

    private val _refreshPlayerCards = MutableLiveData<Boolean>()
    val refreshPlayerCards: LiveData<Boolean> = _refreshPlayerCards


    init {
        baraja.crearBaraja()
        baraja.barajar()
        _handP1.value = mutableListOf()
        _handP2.value = mutableListOf()
    }
    fun getCard() {
        val card = baraja.dameCarta()
        _handP1.value?.add(card)
        _handP2.value?.add(card)
        _refreshPlayerCards.value = false
        refrescarCartas()
    }

    fun refrescarCartas() {
        _refreshPlayerCards.value = !_refreshPlayerCards.value!!
    }








}