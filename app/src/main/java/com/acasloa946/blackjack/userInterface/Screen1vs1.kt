package com.acasloa946.blackjack.userInterface

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.acasloa946.blackjack.data.Carta
import com.acasloa946.blackjack.R


/**
 * @author: Álvaro Castilla
 * Funcion principal de la Screen1vs1
 */
@SuppressLint("MutableCollectionMutableState")
@Composable
fun Pantalla1vs1(NavController: NavController, viewModel1vs1: ViewModel1vs1) {

    val handP1: List<Carta> by viewModel1vs1.handP1.observeAsState(listOf())
    val handP2: List<Carta> by viewModel1vs1.handP2.observeAsState(listOf())
    val refreshPlayerCards: Boolean by viewModel1vs1.refreshPlayerCards.observeAsState(initial = false)
    val refreshTxtTurno: Boolean by viewModel1vs1.refreshTxtTurno.observeAsState(initial = false)
    var showDialogOpciones: Boolean by rememberSaveable { mutableStateOf(false) }
    val ocultarCartas: Boolean by rememberSaveable { mutableStateOf(false) }
    val J1HaTerminado: Boolean by viewModel1vs1.J1HaTerminado.observeAsState(initial = false)
    val J2HaTerminado: Boolean by viewModel1vs1.J2HaTerminado.observeAsState(initial = false)
    var apuestaJugador1 by remember { mutableFloatStateOf(0f) }
    var apuestaJugador2 by remember { mutableFloatStateOf(0f) }
    val refreshApuestas: Boolean by viewModel1vs1.refreshApuestas.observeAsState(initial = false)
    val nombreJ1: String by viewModel1vs1.nombreP1.observeAsState(initial = "Jugador 1")
    val nombreJ2: String by viewModel1vs1.nombreP2.observeAsState(initial = "Jugador 2")
    var showDialogInstrucciones: Boolean by rememberSaveable { mutableStateOf(false) }

    //Columna principal de la Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mat), contentScale = ContentScale.FillWidth
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //llamada a controlar el turno (se ejecuta siempre que se refresca la pantalla)
        viewModel1vs1.controlTurno()
        //llamada a controlar el TxtTurno (se ejecuta siempre que se refresca la pantalla)
        viewModel1vs1.controlTxtTurno()
        //text de turno
        TextoTurno(viewModel1vs1, refreshTxtTurno)
        //row de botones para pedir y pasar.
        RowBotonesJugadores(viewModel1vs1)
        //Espaciador
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        //Texto para mostrar nombre J1
        TextoJugador(player = 1, handP1, handP2, nombreJ1, nombreJ2)
        //LazyRow para mostrar cartas de J1
        ManoJugador1(handP1, refreshPlayerCards)
        //Muestra valor J1
        TextoValor(
            viewModel1vs1 = viewModel1vs1,
            player = 1,
            handP1,
            handP2,
            ocultarCartas,
            J1HaTerminado,
            J2HaTerminado
        )
        //Texto para mostrar nombre J1
        TextoJugador(2, handP1, handP2, nombreJ1, nombreJ2)
        //LazyRow para mostrar cartas de J1
        ManoJugador2(
            handP2 = handP2,
            refreshPlayerCards = refreshPlayerCards,
            ocultarCartas,
            J1HaTerminado,
            J2HaTerminado
        )
        //Muestra valor J2
        TextoValor(
            viewModel1vs1 = viewModel1vs1,
            player = 2,
            handP1,
            handP2,
            ocultarCartas,
            J1HaTerminado,
            J2HaTerminado
        )
        //Boton reiniciar -> solo se muestra cuando la partida ha terminado.
        BotonReiniciar(
            viewModel1vs1 = viewModel1vs1,
            J1HaTerminado,
            J2HaTerminado,
            apuestaJugador1,
            apuestaJugador2,
            changeApuesta1 = {
                apuestaJugador1 = it
            },
            changeApuesta2 = {
                apuestaJugador2 = it
            }
        )
        //Botones de opciones e instrucciones -> solo se muestra cuando la partida no ha iniciado.
        BotonOpcionesInstrucciones(handP1 = handP1, handP2 = handP2, cambiarShowDialogOpciones = {
            showDialogOpciones = !showDialogOpciones
        },
            cambiarShowDialogInstrucciones = {
                showDialogInstrucciones = !showDialogInstrucciones
            })
        //Dialog de las opciones -> solo se muestra cuando se pulsa el botón Opciones.
        DialogOpciones(
            showDialogOpciones = showDialogOpciones, cambiarShowDialogOpciones = {
                showDialogOpciones = !showDialogOpciones
            },
            viewModel1vs1, apuestaJugador1, apuestaJugador2,
            changeApuesta1 = {
                apuestaJugador1 = it
            },
            changeApuesta2 = {
                apuestaJugador2 = it
            },
            refreshApuestas, nombreJ1, nombreJ2
        )
        //Dialog de las instrucciones -> solo se muestra cuando se pulsa el botón instrucciones.
        DialogInstrucciones(
            showDialogInstrucciones = showDialogInstrucciones,
            cambiarShowDialogInstrucciones = {
                showDialogInstrucciones = !showDialogInstrucciones
            })


    }
}

/**
 * Función para mostrar la mano del jugador 1.
 * @param handP1: Mano del jugador 1. Variable del viewmodel1vs1.
 * @param refreshPlayerCards: boolean para refrescar el LazyRow
 */
@Composable
private fun ManoJugador1(handP1: List<Carta>, refreshPlayerCards: Boolean) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(-100.dp)
    ) {
        items(handP1) {
            CrearImagen(foto = it.IdDrawable)
        }
    }
}

/**
 * Función para mostrar la mano del jugador 2.
 * @param handP2: Mano del jugador 2. Variable del viewmodel1vs1.
 * @param refreshPlayerCards: boolean para refrescar el LazyRow. Variable del viewmodel1vs1.
 * @param ocultarCartas: boolean que se cambia en el Dialog de las opciones.
 * @param J1HaTerminado: boolean para saber si el J1HaTerminado. Variable del viewmodel1vs1.
 * @param J2HaTerminado: boolean para saber si el J2HaTerminado. Variable del viewmodel1vs1.
 */
@Composable
private fun ManoJugador2(
    handP2: List<Carta>,
    refreshPlayerCards: Boolean,
    ocultarCartas: Boolean,
    J1HaTerminado: Boolean,
    J2HaTerminado: Boolean
) {
    //si no se quieren ocultar las cartas se muestran las de la mano del J2.
    if (!ocultarCartas || (J1HaTerminado && J2HaTerminado)) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(-100.dp)
        ) {
            items(handP2) {
                CrearImagen(foto = it.IdDrawable)
            }
        }
    } //si se ocultan, se muestra la carta boca abajo.
    else if (ocultarCartas) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(-100.dp)
        ) {
            items(handP2) {
                CrearImagen(foto = R.drawable.facedown)
            }
        }
    }

}

/**
 * Función para pintar la imagen
 */
@Composable
private fun CrearImagen(foto: Int) {
    Image(
        painter = painterResource(id = foto),
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}

/**
 * Función que muestran los botones de pasar y pedir carta.
 * @param viewModel1vs1
 */
@Composable
private fun RowBotonesJugadores(viewModel1vs1: ViewModel1vs1) {
    Row(
    ) {
        Button(
            shape = RectangleShape,
            onClick = {
                viewModel1vs1.getCard()
            },
            colors = ButtonDefaults.buttonColors(Color.Red),
        ) {
            Text(
                text = "Pedir", textAlign = TextAlign.Center, color = Color.Black
            )

        }
        Spacer(modifier = Modifier.padding(3.dp))
        Button(
            shape = RectangleShape,
            onClick = { viewModel1vs1.jugadorPasa() },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "Pasar", textAlign = TextAlign.Center, color = Color.Black
            )
        }
    }
}

/**
 * Función para mostrar el valor de las cartas de los jugadores.
 * @param player: Int para saber el jugador y mostrar su valor.
 * @param handP1: Mano del jugador 1. Variable del viewmodel1vs1.
 * @param handP2: Mano del jugador 2. Variable del viewmodel1vs1.
 * @param ocultarCartas: boolean que se cambia en el Dialog de las opciones.
 * @param J1HaTerminado: boolean para saber si el J1HaTerminado. Variable del viewmodel1vs1.
 * @param J2HaTerminado: boolean para saber si el J2HaTerminado. Variable del viewmodel1vs1.
 */
@Composable
private fun TextoValor(
    viewModel1vs1: ViewModel1vs1,
    player: Int,
    handP1: List<Carta>,
    handP2: List<Carta>,
    ocultarCartas: Boolean,
    J1HaTerminado: Boolean,
    J2HaTerminado: Boolean
) {
    val valorJ1: Int by viewModel1vs1.valorJ1.observeAsState(initial = 0)
    val valorJ2: Int by viewModel1vs1.valorJ2.observeAsState(initial = 0)
    //se muestra cuando no se han sacado cartas.
    if (handP1.isNotEmpty() || handP2.isNotEmpty()) {
        if (player == 1) {
            Text(
                text = "Valor: $valorJ1",
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                fontStyle = FontStyle.Italic
            )
        } else {
            if (!ocultarCartas || (J1HaTerminado && J2HaTerminado)) {
                Text(
                    text = "Valor: $valorJ2",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    fontStyle = FontStyle.Italic
                )
            }
        }

    }
}

/**
 * Función de Text para mostrar el turno actual.
 * @param viewModel1vs1
 * @param refreshTxtTurno: Boolean para refrescar el texto.
 */
@Composable
private fun TextoTurno(viewModel1vs1: ViewModel1vs1, refreshTxtTurno: Boolean) {
    Text(
        text = viewModel1vs1.controlTxtTurno(),
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.White,
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
        fontStyle = FontStyle.Italic
    )
}

/**
 * Función para mostrar el botón reiniciar y controlarlo.
 * @param viewModel1vs1
 * @param J1HaTerminado: boolean para saber si el J1HaTerminado. Variable del viewmodel1vs1.
 * @param J2HaTerminado: boolean para saber si el J2HaTerminado. Variable del viewmodel1vs1.
 * @param apuestaJugador1:
 * @param apuestaJugador2:
 * @param changeApuesta1: función lambda para cambiar la varibale apuesta1
 * @param changeApuesta2: función lambda para cambiar la varibale apuesta2
 */
@Composable
private fun BotonReiniciar(
    viewModel1vs1: ViewModel1vs1,
    J1HaTerminado: Boolean,
    J2HaTerminado: Boolean,
    apuestaJugador1: Float,
    apuestaJugador2: Float,
    changeApuesta1: (Float) -> Unit,
    changeApuesta2: (Float) -> Unit
) {

    val mostrar = true
    val mensajeFinal: String by viewModel1vs1.mensajeFinal.observeAsState(initial = "")


    if (J1HaTerminado && J2HaTerminado && mostrar) {
        viewModel1vs1.msjToastFinal()
        TextoFinal(mensajeFinal)
        Button(
            shape = RectangleShape,
            onClick = {
                viewModel1vs1.controlFichas(apuestaJugador1, apuestaJugador2)
                viewModel1vs1.reiniciar()
                changeApuesta1(0f)
                changeApuesta2(0f)
            },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "Reiniciar", textAlign = TextAlign.Center, color = Color.Black
            )
        }
    }
}

/**
 * Función para mostrar nombre jugador.
 * @param player: Int para saber el jugador y mostrar su valor.
 * @param handP1: Mano del jugador 1. Variable del viewmodel1vs1.
 * @param handP2: Mano del jugador 2. Variable del viewmodel1vs1.
 * @param nombreP1: nombre del J1. Variable del viewmodel1vs1.
 * @param nombreP2: nombre del J2. Variable del viewmodel1vs1.
 */
@Composable
private fun TextoJugador(
    player: Int,
    handP1: List<Carta>,
    handP2: List<Carta>,
    nombreP1: String,
    nombreP2: String
) {
    if (handP1.isNotEmpty() || handP2.isNotEmpty()) {
        if (player == 1) {
            Text(
                text = nombreP1,
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                fontStyle = FontStyle.Italic
            )
        } else {
            Text(
                text = nombreP2,
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.White,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                fontStyle = FontStyle.Italic
            )
        }
    }
}


/**
 * Función para mostrar toast indicando ganador al final
 * @param string: mensaje que se muestra.
 */
@Composable
private fun TextoFinal(string: String) {
    val context = LocalContext.current
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

/**
 * Función para mostrar los botones de opciones e instrucciones.
 */
@Composable
private fun BotonOpcionesInstrucciones(
    handP1: List<Carta>,
    handP2: List<Carta>,
    cambiarShowDialogOpciones: () -> Unit,
    cambiarShowDialogInstrucciones: () -> Unit
) {
    if (handP1.isEmpty() && handP2.isEmpty()) {
        Button(
            shape = RectangleShape,
            modifier = Modifier.size(width = 175.dp, height = 40.dp),
            onClick = { cambiarShowDialogOpciones() },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Opciones", textAlign = TextAlign.Center, color = Color.Black)
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            shape = RectangleShape,
            modifier = Modifier.size(width = 175.dp, height = 40.dp),
            onClick = { cambiarShowDialogInstrucciones()},
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Instrucciones", textAlign = TextAlign.Center, color = Color.Black)
        }
    }
}

/**
 * Función que pinta el Dialog de las opciones.
 */
@Composable
private fun DialogOpciones(
    showDialogOpciones: Boolean,
    cambiarShowDialogOpciones: () -> Unit,
    viewModel1vs1: ViewModel1vs1,
    apuestaJugador1: Float,
    apuestaJugador2: Float,
    changeApuesta1: (Float) -> Unit,
    changeApuesta2: (Float) -> Unit,
    refreshApuesta: Boolean,
    nombreP1: String,
    nombreP2: String
) {
    val ptsJ1: Float by viewModel1vs1.ptsJ1.observeAsState(initial = 0f)
    val ptsJ2: Float by viewModel1vs1.ptsJ2.observeAsState(initial = 0f)


    if (showDialogOpciones) {
        Dialog(onDismissRequest = { cambiarShowDialogOpciones() }) {
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Opciones",
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Nombres",
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                OutlinedJ1(nombreP1, viewModel1vs1)
                OutlinedJ2(nombreP2, viewModel1vs1)
                Text(
                    text = "Apuestas",
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "$apuestaJugador1€",
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )
                Slider(
                    value = apuestaJugador1,
                    valueRange = 0f..ptsJ1,
                    onValueChange = { changeApuesta1(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Red,
                        activeTrackColor = Color.Black,
                        inactiveTrackColor = Color.Red
                    ),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
                Text(
                    text = "$apuestaJugador2€",
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black
                )
                Slider(
                    value = apuestaJugador2,
                    valueRange = 0f..ptsJ2,
                    onValueChange = { changeApuesta2(it) },
                    colors = SliderDefaults.colors(
                        thumbColor = Color.Red,
                        activeTrackColor = Color.Black,
                        inactiveTrackColor = Color.Red
                    ),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )

                Button(
                    shape = RectangleShape,
                    onClick = {
                        cambiarShowDialogOpciones()
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


/**
 * Función que controla el OutlinedTextField para cambiar el nombre del J1.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OutlinedJ1(nombreP1: String, viewModel1vs1: ViewModel1vs1) {
    OutlinedTextField(
        value = nombreP1,
        onValueChange = { viewModel1vs1.cambiarNombreP1(it) },
        modifier = Modifier
            .padding(8.dp),
        label = {
            Text(
                text = "Introduce tu apodo",
                color = Color.Black
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Red
        )
    )
}


/**
 * Función que controla el OutlinedTextField para cambiar el nombre del J2.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OutlinedJ2(nombreP2: String, viewModel1vs1: ViewModel1vs1) {
    OutlinedTextField(
        value = nombreP2,
        onValueChange = { viewModel1vs1.cambiarNombreP2(it) },
        modifier = Modifier
            .padding(8.dp),
        label = {
            Text(
                text = "Introduce tu apodo",
                color = Color.Black
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Red
        )
    )
}


/**
 * Función que muestra el Dialog de las instrucciones.
 */
@Composable
private fun DialogInstrucciones(showDialogInstrucciones: Boolean,
                        cambiarShowDialogInstrucciones: () -> Unit,) {
    if (showDialogInstrucciones) {
        Dialog(onDismissRequest = { cambiarShowDialogInstrucciones() }) {
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(15.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Blackjack",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    fontStyle = FontStyle.Italic
                )
                Text(text = "En el juego del Blackjack, te enfrentas cara a cara en un apasionante duelo 1 contra 1. El objetivo es simple pero desafiante: alcanzar una mano con un valor total lo más cercano posible a 21 sin pasarte. Comienzas sin cartas y decides si pides más cartas para mejorar tu mano o te plantas con lo que tienes. Cada carta numérica tiene su valor nominal y las figuras valen 10.\n" +
                        "\n" +
                        "La estrategia es clave; debes calcular tus posibilidades y evaluar la mano del contrario. ¡Pero cuidado! Pasar de 21 resulta en una derrota automática.",
                    modifier = Modifier.padding(5.dp))
            }
        }
    }

}

    



