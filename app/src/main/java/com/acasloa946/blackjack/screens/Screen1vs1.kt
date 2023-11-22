package com.acasloa946.blackjack.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acasloa946.blackjack.Baraja
import com.acasloa946.blackjack.Carta
import com.acasloa946.blackjack.Jugador
import com.acasloa946.blackjack.Naipes
import com.acasloa946.blackjack.R

var estadoActual: String? = null

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Pantalla1vs1(NavController: NavController) {

    val estado = mutableListOf<String>("TJ1", "TJ2", "Terminado")

    var jugador1haTerminado by rememberSaveable { mutableStateOf(false) }
    var jugador2haTerminado by rememberSaveable { mutableStateOf(false) }

    var jugador1haPasado by rememberSaveable { mutableStateOf(false) }
    var jugador2haPasado by rememberSaveable { mutableStateOf(false) }

    var estadoBotones by rememberSaveable { mutableStateOf(true) }
    var textoTurno by rememberSaveable { mutableStateOf("") }

    val baraja = Baraja

    val jugador1 = Jugador()
    var manoJugador1 by remember { mutableStateOf(jugador1.Mano) }
    var valorJugador1 by rememberSaveable { mutableStateOf(0) }

    val jugador2 = Jugador()
    var manoJugador2 by remember { mutableStateOf(jugador2.Mano) }
    var valorJugador2 by rememberSaveable { mutableStateOf(0) }

    var botonEstadoApuesta by remember { mutableStateOf(true) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mat), contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ControlarTurno(
            jugador1haTerminado, jugador2haTerminado,
            cambiarEstadoBoton = {
                estadoBotones = false
            },
            estadoActual,
            cambiarTextoTurno = {
                textoTurno = it
            },
            jugador1haPasado,
            jugador2haPasado,
            valorJugador1,
            valorJugador2
        )
        Text(
            text = textoTurno,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 30.dp),
            fontStyle = FontStyle.Italic
        )
        RowBotonesJugadores(
            onClickPedir = {
                botonEstadoApuesta = true
                if (jugador1haTerminado) {
                    estadoActual = estado[1]
                }
                if (jugador2haTerminado) {
                    estadoActual = estado[0]
                }
                if (estadoActual == null) {
                    estadoActual = estado[0]
                    baraja.crearBaraja()
                    baraja.barajar()
                }
                if (estadoActual == "TJ1") {
                    val ultimaCarta = baraja.dameCarta()
                    manoJugador1 = manoJugador1.toMutableList().apply { add(ultimaCarta) }
                    if (!jugador1haTerminado) {
                        estadoActual = estado[1]
                    }
                    //controla el uso del AS
                    if (ultimaCarta.Nombre == Naipes.AS) {
                        if ((valorJugador1 + ultimaCarta.PuntosMax) >= 17 && (valorJugador1 + ultimaCarta.PuntosMax) < 21) {
                            valorJugador1 += ultimaCarta.PuntosMax
                        }
                        else if((valorJugador1 + ultimaCarta.PuntosMax) > 21) {
                            valorJugador1 += ultimaCarta.PuntosMin
                        }
                        else {
                            valorJugador1 += ultimaCarta.PuntosMax
                        }
                    }
                    else {
                        valorJugador1 += ultimaCarta.PuntosMax
                    }

                } else if (estadoActual == "TJ2") {
                    val ultimaCarta = baraja.dameCarta()
                    manoJugador2 = manoJugador2.toMutableList().apply { add(ultimaCarta) }
                    if (!jugador1haTerminado) {
                        estadoActual = estado[0]
                    }
                    //control del as
                    if (ultimaCarta.Nombre == Naipes.AS) {
                        if ((valorJugador2 + ultimaCarta.PuntosMax) >= 17 && (valorJugador2 + ultimaCarta.PuntosMax) < 21) {
                            valorJugador2 += ultimaCarta.PuntosMax
                        }
                        else if((valorJugador2 + ultimaCarta.PuntosMax) > 21) {
                            valorJugador2 += ultimaCarta.PuntosMin
                        }
                        else {
                            valorJugador2 += ultimaCarta.PuntosMax
                        }
                    }
                    else {
                        valorJugador2 += ultimaCarta.PuntosMax
                    }
                }


            }, botonEstado = estadoBotones,
            onClickPasar = {
                if (estadoActual == "TJ1") {
                    jugador1haPasado = true
                    jugador1haTerminado = true
                    estadoActual = estado[1]
                } else if (estadoActual == "TJ2") {
                    jugador2haPasado = true
                    jugador2haTerminado = true
                    estadoActual = estado[0]
                }

            }
        )
        ControlFichas(manoJugador1, manoJugador2,
            botonEstadoApuesta,
            cambiarBotonApuesta = {
                botonEstadoApuesta = false
            })

        UIPlayers(
            manoJugador1, manoJugador2, valorJugador1, valorJugador2,
            jugador1haTerminado = {
                jugador1haTerminado = true
            }, jugador2haTerminado = {
                jugador2haTerminado = true
            }
        )
        BotonReiniciar(jugador1haTerminado, jugador2haTerminado,
            onClickReiniciar = {
                baraja.borrarBaraja()
                valorJugador1 = 0
                valorJugador2 = 0
                manoJugador1.clear()
                manoJugador2.clear()
                textoTurno = ""
                estadoBotones = true
                jugador1haPasado = false
                jugador2haPasado = false
                estadoActual = null
                jugador1haTerminado = false
                jugador2haTerminado = false
            })
    }
}


@Composable
fun UIPlayers(
    manoJugador1: MutableList<Carta>,
    manoJugador2: MutableList<Carta>,
    valorJugador1: Int,
    valorJugador2: Int,
    jugador1haTerminado: () -> Unit,
    jugador2haTerminado: () -> Unit
) {
    //jugador 1
    if (manoJugador1.isNotEmpty()) {
        TextoJugador(string = "1")
    }
    //
    LazyRow() {
        items(manoJugador1) { carta ->
            ImagenJugador(foto = carta.IdDrawable)
        }
    }
    if (manoJugador1.size > 4) {
        Row(modifier = Modifier.padding(top = 10.dp)) {
            for (i in 4 until manoJugador1.size) {
                ImagenJugador(foto = manoJugador1[i].IdDrawable)
            }
        }
    }
    if (manoJugador1.isNotEmpty()) {
        TextoValor(valorJugador1, cambiarEstadoJugador = {
            if (valorJugador1 > 21) {
                jugador1haTerminado()
            }
        })
    }


    //jugador2
    Spacer(modifier = Modifier.padding(10.dp))
    if (manoJugador2.isNotEmpty()) {
        TextoJugador(string = "2")
    }
    LazyRow() {
        items(manoJugador2) { carta ->
            ImagenJugador(foto = carta.IdDrawable)
        }
    }
    if (manoJugador2.size > 4) {
        Row(modifier = Modifier.padding(top = 10.dp)) {
            for (i in 4 until manoJugador2.size) {
                ImagenJugador(foto = manoJugador2[i].IdDrawable)
            }
        }
    }
    if (manoJugador2.isNotEmpty()) {
        TextoValor(valorJugador2, cambiarEstadoJugador = {
            if (valorJugador2 > 21) {
                jugador2haTerminado()
            }
        })
    }
}

@Composable
fun ImagenJugador(foto: Int) {
    Image(
        painter = painterResource(id = foto),
        contentDescription = null,
        modifier = Modifier.size(100.dp)
    )
}


@Composable
fun TextoJugador(string: String) {
    Text(
        text = "Jugador $string",
        fontSize = 25.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
private fun RowBotonesJugadores(
    onClickPedir: () -> Unit, botonEstado: Boolean,
    onClickPasar: () -> Unit,
) {
    Row(
        Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            shape = RectangleShape,
            onClick = {
                onClickPedir()
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            enabled = botonEstado
        ) {
            Text(
                text = "Pedir", textAlign = TextAlign.Center, color = Color.Black
            )

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            shape = RectangleShape,
            onClick = { onClickPasar() },
            colors = ButtonDefaults.buttonColors(Color.Red),
            enabled = botonEstado
        ) {
            Text(
                text = "Pasar", textAlign = TextAlign.Center, color = Color.Black
            )
        }
    }
}


@Composable
fun TextoValor(
    numb: Int, cambiarEstadoJugador: () -> Unit
) {
    Text(
        text = "Valor: $numb",
        fontSize = 25.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        modifier = Modifier.padding(top = 10.dp)
    )
    cambiarEstadoJugador()

}

@Composable
fun TextoFinal(txt: String) {
    val context = LocalContext.current
    Toast.makeText(context, txt, Toast.LENGTH_SHORT).show()
}

@Composable
private fun ControlarTurno(
    jugador1haTerminado: Boolean,
    jugador2haTerminado: Boolean,
    cambiarEstadoBoton: () -> Unit,
    estadoActual: String?,
    cambiarTextoTurno: (String) -> Unit,
    jugador1haPasado: Boolean,
    jugador2haPasado: Boolean,
    valorJugador1: Int,
    valorJugador2: Int

) {
    if (jugador1haTerminado && jugador2haTerminado) {
        if (jugador1haPasado && !jugador2haPasado) {
            TextoFinal("La partida ha finalizado. Ha ganado el jugador 1.")
        } else if (!jugador1haPasado && jugador2haPasado) {
            TextoFinal("La partida ha finalizado. Ha ganado el jugador 2.")
        } else if (jugador1haPasado && jugador2haPasado) {
            if (valorJugador1 < valorJugador2) {
                TextoFinal("La partida ha finalizado. Ha ganado el jugador 2.")
            } else if (valorJugador1 == valorJugador2) {
                TextoFinal("Empate.")
            } else {
                TextoFinal("La partida ha finalizado. Ha ganado el jugador 1.")
            }
        } else {
            TextoFinal("La partida ha finalizado. No ha ganado nadie.")
        }
        cambiarTextoTurno("Partida finalizada")
        cambiarEstadoBoton()
    } else if ((estadoActual == "TJ1" && !jugador1haTerminado) || estadoActual == null || (estadoActual == "TJ2" && jugador2haTerminado)) {
        cambiarTextoTurno("Turno: Jugador 1")
    } else if (estadoActual == "TJ2" || (estadoActual == "TJ1" && jugador1haTerminado)) {
        cambiarTextoTurno("Turno: Jugador 2")
    }

}

@Composable
fun BotonReiniciar(
    jugador1haTerminado: Boolean,
    jugador2haTerminado: Boolean,
    onClickReiniciar: () -> Unit
) {

    if (jugador1haTerminado && jugador2haTerminado) {
        Button(
            shape = RectangleShape,
            onClick = {
                onClickReiniciar()
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "Reiniciar", textAlign = TextAlign.Center, color = Color.Black
            )

        }
    }
}

@Composable
fun ControlFichas(
    manoJugador1: MutableList<Carta>,
    manoJugador2: MutableList<Carta>,
    botonEstadoApuesta:Boolean,
    cambiarBotonApuesta : () -> Unit
) {
    var apuestaJugador1 by remember { mutableFloatStateOf(0f) }
    var puntosJugador1 by remember { mutableFloatStateOf(1000f) }

    var apuestaJugador2 by remember { mutableFloatStateOf(0f) }
    var puntosJugador2 by rememberSaveable { mutableStateOf(1000f) }


    if (manoJugador1.isEmpty() && manoJugador2.isEmpty()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            Text(
                text = "Apuestas",
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            TextoJugador(string = "1")

            Text(
                text = "$apuestaJugador1€",
                fontFamily = FontFamily.Monospace,
                color = Color.Black
            )
            Slider(
                value = apuestaJugador1,
                valueRange = 0f..puntosJugador1,
                onValueChange = { apuestaJugador1 = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.Red
                )
            )
            TextoJugador(string = "2")
            Text(
                text = "$apuestaJugador2€",
                fontFamily = FontFamily.Monospace,
                color = Color.Black
            )
            Slider(
                value = apuestaJugador2,
                valueRange = 0f..puntosJugador2,
                onValueChange = { apuestaJugador2 = it },
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Black,
                    inactiveTrackColor = Color.Red
                )
            )

            Button(
                shape = RectangleShape,
                onClick = {
                    puntosJugador1 = puntosJugador1 - apuestaJugador1
                    apuestaJugador1 = 0f
                    puntosJugador2 = puntosJugador2 - apuestaJugador2
                    apuestaJugador2 = 0f
                    cambiarBotonApuesta()
                },
                colors = ButtonDefaults.buttonColors(Color.Red),
                enabled = botonEstadoApuesta
            ) {
                Text(
                    text = "Apostar", textAlign = TextAlign.Center, color = Color.Black
                )

            }


        }
    }
}


