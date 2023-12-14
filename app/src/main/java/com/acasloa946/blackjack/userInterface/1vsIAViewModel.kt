package com.acasloa946.blackjack.userInterface

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.acasloa946.blackjack.data.Baraja
import com.acasloa946.blackjack.data.Carta
import com.acasloa946.blackjack.data.Jugador

/**
 * @author: Álvaro Castilla Loaiza
 * Clase viewmodel que controla la Screen1VSIA
 * @property Baraja: objeto baraja.
 * @property _player1: objeto jugador1.
 * @property _player2: objeto jugador2.
 * @property _nombreP1: nombre jugador1.
 * @property _nombreP2: nombre jugador1.
 * @property _handP1: mano de jugador1.
 * @property _handP2: mano de jugador2.
 * @property _refreshPlayerCards: boolean para refrescar las cartas en LazyRow.
 * @property _refreshTxtTurno: boolean para refrescar texto de turno.
 * @property _estadoJ1: boolean -> true si es su turno, false si no.
 * @property _estadoJ2: boolean -> true si es su turno, false si no.
 * @property _J1HaTerminado: boolean -> true si ha terminado, false si no.
 * @property _J2HaTerminado: boolean -> true si ha terminado, false si no.
 * @property _J1HaPasado: boolean -> true si ha pasado, false si no.
 * @property _J2HaPasado: boolean -> true si ha pasado, false si no.
 * @property _valorJ1: valor de la mano del jugador1.
 * @property _valorJ2: valor de la mano del jugador2.
 * @property _mensajeFinal: mensaje final que se escribe en un toast cuando la partida acaba.
 */
class PvsIAViewModel(application: Application) : AndroidViewModel(application) {

    private val baraja = Baraja

    private val _player1 = MutableLiveData<Jugador>()
    private val _player2 = MutableLiveData<Jugador>()

    private var _nombreP1 = MutableLiveData<String>()
    var nombreP1: LiveData<String> = _nombreP1

    private var _nombreP2 = MutableLiveData<String>()
    var nombreP2: LiveData<String> = _nombreP2

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




// Inicializa las siguientes variables cuando se entra en la Screen
    init {
        baraja.crearBaraja()
        baraja.barajar()
        _handP1.value = mutableListOf()
        _handP2.value = mutableListOf()
        _nombreP1.value = "Jugador 1"
        _nombreP2.value = "IA"
        _player1.value = Jugador(_handP1,0f,_nombreP1.value.toString())
        _player2.value = Jugador(_handP2,0f,_nombreP2.value.toString())
        _valorJ1.value = 0
        _valorJ2.value = 0
        _J1HaTerminado.value = false
        _J2HaTerminado.value = false
        _J1HaPasado.value = false
        _J2HaPasado.value = false
        controlTurno()
        _refreshTxtTurno.value = false


    }

    /**
     * Función para pedir carta y añadirla a la mano.
     */
    fun getCard() {
        //devuelve carta
        val card = baraja.dameCarta()
        //si es el turno del J1 se le añade la carta a su mano.
        if (_estadoJ1.value == true) {
            _handP1.value?.add(card)
        }
        //si no se añade a la mano del J2.
        else if (_estadoJ2.value == true) {
            _handP2.value?.add(card)

        }
        _refreshPlayerCards.value = false
        //se refresca la lazyRow y el texto del valor de cada jugador.
        refrescarCartas()
        calcularTxtValor()
    }

    /**
     * Función para refrescar la LazyRow y que se pinten las cartas que estan en cada mano
     */
    private fun refrescarCartas() {
        _refreshPlayerCards.value = !_refreshPlayerCards.value!!
    }
    /**
     * Función para refrescar el Text que muestra el turno actual.
     */
    private fun refrescarTxtTurno() {
        _refreshTxtTurno.value = !_refreshTxtTurno.value!!
    }


    /**
     * Función para controlar el turno en cada momento.
     */
    fun controlTurno() {
        //si el valor de la mano del J1 es mayor a 21 o ha pasado, deja de jugar (_J1HaTerminado = true)
        if (valorJ1.value!! > 21 || _J1HaPasado.value == true) {
            _J1HaTerminado.value = true
        }
        //si el valor de la mano del J2 es mayor a 21 o ha pasado, deja de jugar (_J2HaTerminado = true)
        if (valorJ2.value!! > 21 || _J2HaPasado.value == true) {
            _J2HaTerminado.value = true
        }
        //si el J1 ha terminado no puede tener turno.
        if (_J1HaTerminado.value == true) {
            _estadoJ1.value = false
        }
        //si el J2 ha terminado no puede tener turno.
        if (_J2HaTerminado.value == true) {
            _estadoJ2.value = false
        }
        //controla estado inicial -> ambas manos estan vacías.
        if (_handP1.value?.isEmpty()!! && _handP2.value?.isEmpty()!!) {
            _estadoJ1.value = true
            _estadoJ2.value = false
        } //si el J1 ha terminado y el J2 no, es turno del J2.
        else if (_estadoJ1.value == false && _J1HaTerminado.value == true && _J2HaTerminado.value == false) {
            _estadoJ2.value = true
        } //si el J2 ha terminado y el J1 no, es turno del J1.
        else if (_estadoJ2.value == false && _J2HaTerminado.value == true && _J1HaTerminado.value == false) {
            _estadoJ1.value = true
        } //si es turno de J1 y el J2 no ha terminado, cuando se llame a pedir carta se cambiara de turno.
        else if (_estadoJ1.value == true && _J2HaTerminado.value == false) {
            _estadoJ1.value = false
            _estadoJ2.value = true
        } //si es turno de J2 y el J1 no ha terminado, cuando se llame a pedir carta se cambiara de turno.
        else if (_estadoJ2.value == true && _J1HaTerminado.value == false) {
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

    /**
     * Función para controlar el estado de la IA y lo que debe hacer.
     */
    fun controlIA() {
        //controla si es el turno de la IA
        if (_estadoJ2.value == true && _estadoJ1.value == false) {
            //mientras que el valor no sea mayor de 17, pedirá carta.
            if (_valorJ2.value!!<17) {
                getCard()
            }
            //si no, pasará.
            else {
                jugadorPasa()
            }
        }
    }

    /**
     * Función para calcular los valores de cada mano
     */
    private fun calcularTxtValor() {
        _valorJ1.value = 0
        _valorJ2.value = 0
        for (carta in _handP1.value!!) {
            _valorJ1.value = valorJ1.value?.plus(carta.PuntosMin)
        }
        for (carta in _handP2.value!!) {
            _valorJ2.value = valorJ2.value?.plus(carta.PuntosMin)
        }
    }

    /**
     * Función para controlar el Text del turno.
     */
    fun controlTxtTurno(): String {
        if (_estadoJ1.value == true && _estadoJ2.value == false) {
            return "Turno -> ${_nombreP1.value}"
        } else if (_estadoJ2.value == true && _estadoJ1.value == false) {
            return "Turno -> ${_nombreP2.value}"
        }
        return "Partida finalizada"
    }
    /**
     * Función para controlar cuando un jugador pasa.
     */
    fun jugadorPasa() {
        //si pasa el J1 y el J2 no ha terminado, turno de J2
        if (_estadoJ1.value == true && (_J2HaTerminado.value == false || _J2HaPasado.value==true)) {
            _J1HaPasado.value = true
            _estadoJ2.value = true
            _estadoJ1.value = false
        }  //si pasa el J2 y el J1 no ha terminado, turno de J1
        else if (_estadoJ2.value == true && (_J1HaTerminado.value == false || _J1HaPasado.value==true)) {
            _J2HaPasado.value = true
            _estadoJ1.value = true
            _estadoJ2.value = false
        } //si pasa el J1 y el J2 ha terminado, termina.
        else if (_estadoJ1.value == true && _J2HaTerminado.value == true) {
            _J1HaPasado.value = true
            _estadoJ1.value = false
            _estadoJ2.value = false
        }//si pasa el J2 y el J1 ha terminado, termina.
        else if (_estadoJ2.value == true && _J1HaTerminado.value == true) {
            _J2HaPasado.value = true
            _estadoJ1.value = false
            _estadoJ2.value = false
        }
        refrescarTxtTurno()
        controlIA()
    }

    /**
     * Función asociada al botón reiniciar (mirar Screen1VSIA). Reinicia partida cuando esta ha terminado.
     */
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
        _mensajeFinal.value = ""
        _player1.value = Jugador(_handP1,0f,_nombreP1.value.toString())
        _player2.value = Jugador(_handP2,0f,_nombreP2.value.toString())
    }

    /**
     * Función que cambia el _mensajeFinal para dar ganador.
     */
    fun msjToastFinal() {
        if (_J1HaTerminado.value == true && _J2HaTerminado.value == true) {
            if (_J1HaPasado.value==true && _J2HaPasado.value==true) {
                if (_valorJ1.value!! > _valorJ2.value!!) {
                    _mensajeFinal.value = "Ha ganado ${_nombreP1.value}"
                }
                else if (_valorJ1.value!! < _valorJ2.value!!) {
                    _mensajeFinal.value = "Ha ganado la ${_nombreP2.value}"
                }
                else if (_valorJ1.value!! == _valorJ2.value!!) {
                    _mensajeFinal.value = "Empate."
                }
            }
            else if (_J1HaPasado.value==true && _J2HaPasado.value==false) {
                _mensajeFinal.value = "Ha ganado ${_nombreP1.value}"
            }
            else if (_J1HaPasado.value==false && _J2HaPasado.value==true) {
                _mensajeFinal.value = "Ha ganado la ${_nombreP2.value}"
            }
            else if (_J1HaPasado.value==false && _J2HaPasado.value==false) {
                _mensajeFinal.value = "La partida ha finalizado. No ha  ganado nadie."
            }
        }

    }

    /**
     * Función para cambiar el nombre del J1 (mirar DialogOpciones en Screen1VSIA)
     */
    fun cambiarNombreP1(nombreP1:String) {
        _nombreP1.value = nombreP1
    }




}