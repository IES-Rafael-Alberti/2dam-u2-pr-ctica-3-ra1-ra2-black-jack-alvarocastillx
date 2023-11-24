package com.acasloa946.blackjack.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.acasloa946.blackjack.Baraja
import com.acasloa946.blackjack.Carta
import com.acasloa946.blackjack.Jugador
import com.acasloa946.blackjack.Naipes
import com.acasloa946.blackjack.R


@SuppressLint("MutableCollectionMutableState")
@Composable
fun Pantalla1vsIA(NavController: NavController) {
    val estado = mutableListOf<String>("TJ1", "TJ2", "Terminado")

    var jugador1haTerminado by rememberSaveable { mutableStateOf(false) }
    var IAhaTerminado by rememberSaveable { mutableStateOf(false) }

    var jugador1haPasado by rememberSaveable { mutableStateOf(false) }
    var IAhaPasado by rememberSaveable { mutableStateOf(false) }

    var estadoBotones by rememberSaveable { mutableStateOf(true) }
    var textoTurno by rememberSaveable { mutableStateOf("") }

    val baraja = Baraja

    val jugador1 = Jugador()
    var manoJugador1 by remember { mutableStateOf(jugador1.Mano) }
    var valorJugador1 by rememberSaveable { mutableStateOf(0) }

    val IA = Jugador()
    var manoIA by remember { mutableStateOf(IA.Mano) }
    var valorIA by rememberSaveable { mutableStateOf(0) }

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var ocultarCartas by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mat), contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
                if (jugador1haTerminado) {
                    estadoActual = estado[1]
                }
                if (IAhaTerminado) {
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

                }


            }, botonEstado = estadoBotones,
            onClickPasar = {
                if (estadoActual == "TJ1") {
                    jugador1haPasado = true
                    jugador1haTerminado = true
                    estadoActual = estado[1]
                }

            }
        )
        ControlarTurnoIA(
            jugador1haTerminado,
            IAhaTerminado,
            cambiarEstadoBoton = {
                estadoBotones = false
            },
            estadoActual,
            cambiarTextoTurno = {
                textoTurno = it
            },
            jugador1haPasado,
            IAhaPasado,
            valorJugador1,
            valorIA,
            baraja,
            estado,
            sumarValorIA = {
                if (it.Nombre == Naipes.AS) {
                    if ((valorIA + it.PuntosMax) >= 17 && (valorIA + it.PuntosMax) < 21) {
                        valorIA += it.PuntosMax
                    }
                    else if((valorIA + it.PuntosMax) > 21) {
                        valorIA += it.PuntosMin
                    }
                    else {
                        valorIA += it.PuntosMax
                    }
                }
                else {
                    valorIA += it.PuntosMax
                }
            },
            cambiarManoIA = {
                manoIA = manoIA.toMutableList().apply { add(it) }
            },
            IAPasa = {
                IAhaPasado = true
                IAhaTerminado = true
                estadoActual = estado[0]
            }
        )
        Opciones(manoJugador1,manoIA,
            cambiarShowDialog = {
                showDialog = true
            })
        DialogOpciones(showDialog = showDialog,
            cambiarShowDialog = {
                showDialog = false
            },
            ocultarCartas,
            onCheckedMostrarCartas = {
                ocultarCartas = it
            })
        UIPlayers(
            manoJugador1, manoIA, valorJugador1, valorIA,
            changeJugador1haTerminado = {
                jugador1haTerminado = true
            }, changeJugador2haTerminado = {
                IAhaTerminado = true
            },jugador1haTerminado,IAhaTerminado,ocultarCartas
        )
        BotonReiniciar(jugador1haTerminado, IAhaTerminado,
            onClickReiniciar = {
                baraja.borrarBaraja()
                valorJugador1 = 0
                valorIA = 0
                manoJugador1.clear()
                manoIA.clear()
                textoTurno = ""
                estadoBotones = true
                jugador1haPasado = false
                IAhaPasado = false
                estadoActual = null
                jugador1haTerminado = false
                IAhaTerminado = false
            })
    }
}

@Composable
private fun ControlarTurnoIA(
    jugador1haTerminado: Boolean,
    IAhaTerminado: Boolean,
    cambiarEstadoBoton: () -> Unit,
    estadoActual: String?,
    cambiarTextoTurno: (String) -> Unit,
    jugador1haPasado: Boolean,
    IAhaPasado: Boolean,
    valorJugador1: Int,
    valorIA:Int,
    baraja: Baraja.Companion,
    estado : MutableList<String>,
    sumarValorIA : (Carta) -> Unit,
    cambiarManoIA : (Carta) -> Unit,
    IAPasa : () -> Unit


) {
    if (jugador1haTerminado && IAhaTerminado) {
        if (jugador1haPasado && !IAhaPasado) {
            TextoFinal("La partida ha finalizado. Ha ganado el jugador 1.")
        } else if (!jugador1haPasado && IAhaPasado) {
            TextoFinal("La partida ha finalizado. Ha ganado la IA.")
        } else if (jugador1haPasado && IAhaPasado) {
            if (valorJugador1 < valorIA) {
                TextoFinal("La partida ha finalizado. Ha ganado la IA.")
            } else if (valorJugador1 == valorIA) {
                TextoFinal("Empate.")
            } else {
                TextoFinal("La partida ha finalizado. Ha ganado el jugador 1.")
            }
        } else {
            TextoFinal("La partida ha finalizado. No ha ganado nadie.")
        }
        cambiarTextoTurno("Partida finalizada")
        cambiarEstadoBoton()
    } else if ((estadoActual == "TJ1" && !jugador1haTerminado) || estadoActual == null || (estadoActual == "TJ2" && IAhaTerminado)) {
        cambiarTextoTurno("Turno: Jugador 1")
    } else if (estadoActual == "TJ2" || (estadoActual == "TJ1" && jugador1haTerminado)) {
        cambiarTextoTurno("Turno: IA")
    }
    ////
    if (estadoActual== "TJ2") {
        if (valorIA>=17) {
            IAPasa()
        }
        if (valorIA < 17){
            val ultimaCarta = baraja.dameCarta()
            cambiarManoIA(ultimaCarta)
            if (!jugador1haTerminado) {
                com.acasloa946.blackjack.screens.estadoActual = estado[0]
            }
            sumarValorIA(ultimaCarta)
        }

    }

}
@Composable
private fun ImagenJugador(foto:Int) {
    Image(
        painter = painterResource(id = foto),
        contentDescription = null,
        modifier = Modifier.size(100.dp)
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
private fun UIPlayers(
    manoJugador1: MutableList<Carta>,
    manoJugador2: MutableList<Carta>,
    valorJugador1: Int,
    valorJugador2: Int,
    changeJugador1haTerminado: () -> Unit,
    changeJugador2haTerminado: () -> Unit,
    jugador1haTerminado: Boolean,
    IAhaTerminado: Boolean,
    ocultarCartas: Boolean

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
                changeJugador1haTerminado()
            }
        })
    }


    //jugador2
    Spacer(modifier = Modifier.padding(10.dp))
    if (manoJugador2.isNotEmpty()) {
        TextoJugador(string = "2")
    }
    if (!ocultarCartas || (jugador1haTerminado && IAhaTerminado)) {
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
    }
    else if(ocultarCartas) {
        LazyRow() {
            items(manoJugador2) { carta ->
                ImagenJugador(foto = R.drawable.facedown)
            }
        }
        if (manoJugador2.size > 4) {
            Row(modifier = Modifier.padding(top = 10.dp)) {
                for (i in 4 until manoJugador2.size) {
                    ImagenJugador(foto = R.drawable.facedown)
                }
            }
        }
    }

    if (manoJugador2.isNotEmpty()) {
        if (!ocultarCartas) {
            TextoValor(valorJugador2, cambiarEstadoJugador = {
                if (valorJugador2 > 21) {
                    changeJugador2haTerminado()
                }
            })
        }
    }

}

@Composable
fun Opciones(
    manoJugador1: MutableList<Carta>,
    manoJugador2: MutableList<Carta>,
    cambiarShowDialog : () -> Unit

) {



    if (manoJugador1.isEmpty() && manoJugador2.isEmpty()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(30.dp)
        ) {
            Button(
                shape = RectangleShape,
                onClick = {
                    cambiarShowDialog()
                },
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(
                    text = "Opciones", textAlign = TextAlign.Center, color = Color.Black
                )

            }



        }
    }
}
@Composable
fun DialogOpciones(
    showDialog : Boolean,
    cambiarShowDialog : () -> Unit,
    mostrarCartas : Boolean,
    onCheckedMostrarCartas : (Boolean) -> Unit
) {

    if (showDialog) {
        Dialog(onDismissRequest = { cambiarShowDialog() }) {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Esconder cartas de IA",
                    color = Color.White
                )
                Checkbox(checked = mostrarCartas, onCheckedChange = {
                    onCheckedMostrarCartas(it)
                },
                    colors = CheckboxDefaults.colors(Color.Red)
                )
                Button(
                    shape = RectangleShape,
                    onClick = {
                        cambiarShowDialog()
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text(
                        text = "Guardar y salir", textAlign = TextAlign.Center, color = Color.White
                    )

                }
            }
        }
    }
}