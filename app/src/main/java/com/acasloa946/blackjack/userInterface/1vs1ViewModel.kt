package com.acasloa946.blackjack.userInterface

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acasloa946.blackjack.data.Baraja
import com.acasloa946.blackjack.data.Carta
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_OFF

class ViewModel1vs1(application: Application) : AndroidViewModel(application) {

    private val baraja = Baraja


    private var _handP1 = MutableLiveData<MutableList<Carta>>()
    var handP1: LiveData<MutableList<Carta>> = _handP1

    private var _handP2 = MutableLiveData<MutableList<Carta>>()
    var handP2: LiveData<MutableList<Carta>> = _handP2

    private val _refreshPlayerCards = MutableLiveData<Boolean>()
    val refreshPlayerCards: LiveData<Boolean> = _refreshPlayerCards

    private val _refreshTxtTurno = MutableLiveData<Boolean>()
    val refreshTxtTurno: LiveData<Boolean> = _refreshTxtTurno

    private val _estadoJ1 = MutableLiveData<Boolean>()

    private val _estadoJ2 = MutableLiveData<Boolean>()

    private val _J1HaTerminado = MutableLiveData<Boolean>()
    val J1HaTerminado: LiveData<Boolean> = _J1HaTerminado

    private val _J2HaTerminado = MutableLiveData<Boolean>()
    val J2HaTerminado: LiveData<Boolean> = _J2HaTerminado

    private val _J1HaPasado = MutableLiveData<Boolean>()

    private val _J2HaPasado = MutableLiveData<Boolean>()

    private var _valorJ1 = MutableLiveData<Int>()
    val valorJ1: LiveData<Int> = _valorJ1

    private var _valorJ2 = MutableLiveData<Int>()
    val valorJ2: LiveData<Int> = _valorJ2

    private var _mensajeFinal = MutableLiveData<String>()
    val mensajeFinal: LiveData<String> = _mensajeFinal



    init {
        baraja.crearBaraja()
        baraja.barajar()
        _handP1.value = mutableListOf()
        _handP2.value = mutableListOf()
        _valorJ1.value = 0
        _valorJ2.value = 0
        _J1HaTerminado.value = false
        _J2HaTerminado.value = false
        _J1HaPasado.value = false
        _J2HaPasado.value = false
        controlTurno()
        _refreshTxtTurno.value = false
    }

    fun getCard() {
        val card = baraja.dameCarta()
        if (_estadoJ1.value == true) {
            _handP1.value?.add(card)
        } else if (_estadoJ2.value == true) {
            _handP2.value?.add(card)

        }
        _refreshPlayerCards.value = false
        refrescarCartas()
        calcularTxtValor()
    }

    fun refrescarCartas() {
        _refreshPlayerCards.value = !_refreshPlayerCards.value!!
    }

    fun refrescarTxtTurno() {
        _refreshTxtTurno.value = !_refreshTxtTurno.value!!
    }

    fun controlTurno() {

        if (valorJ1.value!! > 21 || _J1HaPasado.value == true) {
            _J1HaTerminado.value = true
        }
        if (valorJ2.value!! > 21 || _J2HaPasado.value == true) {
            _J2HaTerminado.value = true
        }
        if (_J1HaTerminado.value == true) {
            _estadoJ1.value = false
        }
        if (_J2HaTerminado.value == true) {
            _estadoJ2.value = false
        }


        if (_handP1.value?.isEmpty()!! && _handP2.value?.isEmpty()!!) {
            _estadoJ1.value = true
            _estadoJ2.value = false
        } else if (_estadoJ1.value == false && _J1HaTerminado.value == true && _J2HaTerminado.value == false) {
            _estadoJ2.value = true
        } else if (_estadoJ2.value == false && _J2HaTerminado.value == true && _J1HaTerminado.value == false) {
            _estadoJ1.value = true
        } else if (_estadoJ1.value == true && _J2HaTerminado.value == false) {
            _estadoJ1.value = false
            _estadoJ2.value = true
        } else if (_estadoJ2.value == true && _J1HaTerminado.value == false) {
            _estadoJ1.value = true
            _estadoJ2.value = false
        } else if (_estadoJ1.value == true && _J2HaTerminado.value == true) {
            _estadoJ1.value = true
            _estadoJ2.value = false
        } else if (_estadoJ2.value == true && _J1HaTerminado.value == true) {
            _estadoJ1.value = false
            _estadoJ2.value = true
        }
    }

    fun calcularTxtValor() {
        _valorJ1.value = 0
        _valorJ2.value = 0
        for (carta in _handP1.value!!) {
            _valorJ1.value = valorJ1.value?.plus(carta.PuntosMin)
        }
        for (carta in _handP2.value!!) {
            _valorJ2.value = valorJ2.value?.plus(carta.PuntosMin)
        }
    }

    fun controlTxtTurno(): String {
        if (_estadoJ1.value == true && _estadoJ2.value == false) {
            return "Turno -> Jugador 1"
        } else if (_estadoJ2.value == true && _estadoJ1.value == false) {
            return "Turno -> Jugador 2"
        }
        return "Partida finalizada"
    }

    fun jugadorPasa() {
        if (_estadoJ1.value == true && (_J2HaTerminado.value == false || _J2HaPasado.value==true)) {
            _J1HaPasado.value = true
            _estadoJ2.value = true
            _estadoJ1.value = false

        } else if (_estadoJ2.value == true && (_J1HaTerminado.value == false || _J1HaPasado.value==true)) {
            _J2HaPasado.value = true
            _estadoJ1.value = true
            _estadoJ2.value = false
        }
        else if (_estadoJ1.value == true && _J2HaTerminado.value == true) {
            _J1HaPasado.value = true
            _estadoJ1.value = false
            _estadoJ2.value = false
        }
        else if (_estadoJ2.value == true && _J1HaTerminado.value == true) {
            _J2HaPasado.value = true
            _estadoJ1.value = false
            _estadoJ2.value = false
        }
        refrescarTxtTurno()
    }

    fun reiniciar() {
        _estadoJ1.value = true
        _estadoJ2.value = false
        baraja.borrarBaraja()
        controlTurno()
        baraja.crearBaraja()
        baraja.barajar()
        _handP1.value = mutableListOf()
        _handP2.value = mutableListOf()
        _J1HaPasado.value = false
        _J2HaPasado.value = false
        _valorJ1.value = 0
        _valorJ2.value = 0
        _J1HaTerminado.value = false
        _J2HaTerminado.value = false
        _refreshTxtTurno.value = false
    }

    fun msjToastFinal() {
        if (_J1HaTerminado.value == true && _J2HaTerminado.value == true) {
            if (_J1HaPasado.value==true && _J2HaPasado.value==true) {
                if (_valorJ1.value!! > _valorJ2.value!!) {
                    _mensajeFinal.value = "Ha ganado el jugador 1"
                }
                else if (_valorJ1.value!! < _valorJ2.value!!) {
                    _mensajeFinal.value = "Ha ganado el jugador 2"
                }
                else if (_valorJ1.value!! == _valorJ2.value!!) {
                    _mensajeFinal.value = "Empate."
                }
            }
            else if (_J1HaPasado.value==true && _J2HaPasado.value==false) {
                _mensajeFinal.value = "Ha ganado el jugador 1."
            }
            else if (_J1HaPasado.value==false && _J2HaPasado.value==true) {
                _mensajeFinal.value = "Ha ganado el jugador 2."
            }
            else if (_J1HaPasado.value==false && _J2HaPasado.value==false) {
                _mensajeFinal.value = "La partida ha finalizado. No ha  ganado nadie."
            }
        }
    }


}