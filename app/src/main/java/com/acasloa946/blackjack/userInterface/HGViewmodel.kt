package com.acasloa946.blackjack.userInterface

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acasloa946.blackjack.R
import com.acasloa946.blackjack.data.Baraja
import com.acasloa946.blackjack.data.Carta

class HGViewmodel(application: Application) : AndroidViewModel(application) {

    val baraja = Baraja
    private var _cartaNueva = MutableLiveData<Carta>()
    var cartaNueva : LiveData<Carta> = _cartaNueva

    private var _imagenID =  MutableLiveData<Int>()
    var imagenID : LiveData<Int> = _imagenID



    fun onClickDameCarta() {
        if (baraja.listaCartas.size == 1 || baraja.listaCartas.isEmpty()) {
            baraja.crearBaraja()
            baraja.barajar()
            _cartaNueva.value = Baraja.listaCartas.last()
        }
        // si la imagen que se esta mostrando es la carta boca abajo y se le da al botón damecarta se cambia la imagenID por la de la última carta
        if (imagenID.value == R.drawable.facedown) {
            _imagenID.value = baraja.listaCartas.last().IdDrawable
        }
        //si es la última carta y se pulsa el botón se muestra un toast
        else if (Baraja.ultimaCarta) {
            Baraja.ultimaCarta = false
        }
        //si no se esta mostrando la carta boca abajo ni es la última carta se accede al método dameCarta y se cambia el imagenID.
        else {
            _cartaNueva.value = Baraja.dameCarta()
            _imagenID.value = _cartaNueva.value!!.IdDrawable
        }
    }
    fun onClickReiniciar() {
        //borra baraja
        Baraja.borrarBaraja()
        //crea baraja
        Baraja.crearBaraja()
        //baraja
        Baraja.barajar()
        //se cambia el imagenIDÇ
        _imagenID.value = R.drawable.facedown
    }
}