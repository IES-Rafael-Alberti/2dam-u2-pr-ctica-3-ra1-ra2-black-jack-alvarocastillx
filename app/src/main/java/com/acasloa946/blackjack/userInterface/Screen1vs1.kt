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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Pantalla1vs1(NavController: NavController, viewModel1vs1: ViewModel1vs1) {

    val handP1: List<Carta> by viewModel1vs1.handP1.observeAsState(listOf())
    val handP2: List<Carta> by viewModel1vs1.handP2.observeAsState(listOf())
    val refreshPlayerCards: Boolean by viewModel1vs1.refreshPlayerCards.observeAsState(initial = false)
    val refreshTxtTurno: Boolean by viewModel1vs1.refreshTxtTurno.observeAsState(initial = false)
    var showDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    var ocultarCartas: Boolean by rememberSaveable { mutableStateOf(false) }
    val J1HaTerminado: Boolean by viewModel1vs1.J1HaTerminado.observeAsState(initial = false)
    val J2HaTerminado: Boolean by viewModel1vs1.J2HaTerminado.observeAsState(initial = false)
    var apuestaJugador1 by remember { mutableFloatStateOf(0f) }
    var apuestaJugador2 by remember { mutableFloatStateOf(0f) }
    val refreshApuestas: Boolean by viewModel1vs1.refreshApuestas.observeAsState(initial = false)
    val nombreJ1: String by viewModel1vs1.nombreP1.observeAsState(initial = "Jugador 1")
    val nombreJ2: String by viewModel1vs1.nombreP2.observeAsState(initial = "Jugador 2")





    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mat), contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel1vs1.controlTurno()
        viewModel1vs1.controlTxtTurno()
        TextoTurno(viewModel1vs1, refreshTxtTurno)
        RowBotonesJugadores(viewModel1vs1)
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        TextoJugador(player = 1, handP1, handP2, nombreJ1, nombreJ2)
        ManoJugador1(handP1, refreshPlayerCards)
        TextoValor(
            viewModel1vs1 = viewModel1vs1,
            player = 1,
            handP1,
            handP2,
            ocultarCartas,
            J1HaTerminado,
            J2HaTerminado
        )
        TextoJugador(2, handP1, handP2, nombreJ1, nombreJ2)
        ManoJugador2(
            handP2 = handP2,
            refreshPlayerCards = refreshPlayerCards,
            ocultarCartas,
            J1HaTerminado,
            J2HaTerminado
        )
        TextoValor(
            viewModel1vs1 = viewModel1vs1,
            player = 2,
            handP1,
            handP2,
            ocultarCartas,
            J1HaTerminado,
            J2HaTerminado
        )
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
        BotonOpciones(handP1 = handP1, handP2 = handP2, cambiarShowDialog = {
            showDialog = !showDialog
        })
        DialogOpciones(
            showDialog = showDialog, cambiarShowDialog = {
                showDialog = !showDialog
            },
            ocultarCartas,
            cambiarOcultarCartas = {
                ocultarCartas = it
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


    }
}


@Composable
fun ManoJugador1(handP1: List<Carta>, refreshPlayerCards: Boolean) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(-100.dp)
    ) {
        items(handP1) {
            CrearImagen(foto = it.IdDrawable)
        }
    }
}

@Composable
fun ManoJugador2(
    handP2: List<Carta>,
    refreshPlayerCards: Boolean,
    ocultarCartas: Boolean,
    J1HaTerminado: Boolean,
    J2HaTerminado: Boolean
) {
    if (!ocultarCartas || (J1HaTerminado && J2HaTerminado)) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(-100.dp)
        ) {
            items(handP2) {
                CrearImagen(foto = it.IdDrawable)
            }
        }
    } else if (ocultarCartas) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(-100.dp)
        ) {
            items(handP2) {
                CrearImagen(foto = R.drawable.facedown)
            }
        }
    }

}

@Composable
fun CrearImagen(foto: Int) {
    Image(
        painter = painterResource(id = foto),
        contentDescription = null,
        modifier = Modifier.size(150.dp)
    )
}

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
        Spacer(modifier = Modifier.padding(10.dp))
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

@Composable
fun TextoValor(
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

    if (handP1.isNotEmpty() || handP2.isNotEmpty()) {
        if (player == 1) {
            Text(
                text = "Valor: $valorJ1",
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                fontStyle = FontStyle.Italic
            )
        } else {
            if (!ocultarCartas || (J1HaTerminado && J2HaTerminado)) {
                Text(
                    text = "Valor: $valorJ2",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    fontStyle = FontStyle.Italic
                )
            }
        }

    }
}

@Composable
fun TextoTurno(viewModel1vs1: ViewModel1vs1, refreshTxtTurno: Boolean) {
    Text(
        text = viewModel1vs1.controlTxtTurno(),
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun BotonReiniciar(
    viewModel1vs1: ViewModel1vs1,
    J1HaTerminado: Boolean,
    J2HaTerminado: Boolean,
    apuestaJugador1: Float,
    apuestaJugador2: Float,
    changeApuesta1: (Float) -> Unit,
    changeApuesta2: (Float) -> Unit
) {

    var mostrar = true
    val mensajeFinal: String by viewModel1vs1.mensajeFinal.observeAsState(initial = "")


    if (J1HaTerminado && J2HaTerminado && mostrar) {
        viewModel1vs1.msjToastFinal()
        TextoFinal(mensajeFinal)
        mostrar = false
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

@Composable
fun TextoJugador(
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
                color = Color.Black,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                fontStyle = FontStyle.Italic
            )
        } else {
            Text(
                text = nombreP2,
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Black,
                modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun TextoFinal(string: String) {
    val context = LocalContext.current
    Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
}

@Composable
fun BotonOpciones(handP1: List<Carta>, handP2: List<Carta>, cambiarShowDialog: () -> Unit) {
    if (handP1.isEmpty() && handP2.isEmpty()) {
        Button(
            shape = RectangleShape,
            onClick = { cambiarShowDialog() },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(text = "Opciones", textAlign = TextAlign.Center, color = Color.Black)
        }
    }
}

@Composable
fun DialogOpciones(
    showDialog: Boolean,
    cambiarShowDialog: () -> Unit,
    ocultarCartas: Boolean,
    cambiarOcultarCartas: (Boolean) -> Unit,
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


    if (showDialog) {
        Dialog(onDismissRequest = { cambiarShowDialog() }) {
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
                    text = "Esconder cartas",
                    color = Color.Black
                )
                Checkbox(
                    checked = ocultarCartas, onCheckedChange = {
                        cambiarOcultarCartas(it)
                    },
                    colors = CheckboxDefaults.colors(Color.Red)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedJ1(nombreP1: String, viewModel1vs1: ViewModel1vs1) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedJ2(nombreP2: String, viewModel1vs1: ViewModel1vs1) {
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
    



