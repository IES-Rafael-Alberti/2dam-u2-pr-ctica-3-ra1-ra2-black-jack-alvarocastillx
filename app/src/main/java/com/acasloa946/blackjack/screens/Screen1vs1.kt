package com.acasloa946.blackjack.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acasloa946.blackjack.Baraja
import com.acasloa946.blackjack.Jugador
import com.acasloa946.blackjack.R

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Pantalla1vs1(NavController: NavController) {
    var jugador1haSacado by rememberSaveable { mutableStateOf(false) }
    var jugador2haSacado by rememberSaveable { mutableStateOf(false) }

    var boton1Estado by rememberSaveable { mutableStateOf(true) }
    var boton2Estado by rememberSaveable { mutableStateOf(true) }

    val baraja = Baraja()

    val jugador1 = Jugador()
    var manoJugador1 by remember { mutableStateOf(jugador1.Mano) }
    var valorJugador1 by rememberSaveable { mutableStateOf(0) }

    val jugador2 = Jugador()
    var manoJugador2 by remember { mutableStateOf(jugador2.Mano) }
    var valorJugador2 by rememberSaveable { mutableStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mat), contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //jugador 1
        TextoJugador(number = 1)
        RowBotonesJugador1(
            onClickPedir = {
                if (baraja.listaCartas.isEmpty()) {
                    baraja.crearBaraja()
                    baraja.barajar()
                    val ultimaCarta = baraja.dameCarta()
                    valorJugador1 += ultimaCarta.PuntosMax
                    manoJugador1 = manoJugador1.toMutableList().apply { add(ultimaCarta) }
                } else {
                    val ultimaCarta = baraja.dameCarta()
                    manoJugador1 = manoJugador1.toMutableList().apply { add(ultimaCarta) }
                }
                //Control de turno
                jugador1haSacado = true
                controlarTurno(
                    cambiarBoton1Estado = {
                        boton1Estado = false
                    },
                    cambiarBoton2Estado = {
                        boton2Estado=false
                    },
                    jugador1haSacado,
                    jugador2haSacado,
                    reiniciarBotones = {
                        boton1Estado = true
                        boton2Estado = true
                    }
                )

            }, boton1Estado = boton1Estado,
            onClickPasar = {

            }
        )
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
        TextoValor(valorJugador1, cambiarEstadoJugador = {
            if (valorJugador1 >= 21) {
                boton1Estado = false
            }
        })


        //jugador2
        Spacer(modifier = Modifier.padding(10.dp))
        TextoJugador(number = 2)
        RowBotonesJugador2(
            onClickPedir = {
                if (baraja.listaCartas.isEmpty()) {
                    baraja.crearBaraja()
                    baraja.barajar()
                    val ultimaCarta = baraja.dameCarta()
                    valorJugador2 += ultimaCarta.PuntosMax
                    manoJugador2 = manoJugador2.toMutableList().apply { add(ultimaCarta) }
                } else {
                    val ultimaCarta = baraja.dameCarta()
                    manoJugador2 = manoJugador2.toMutableList().apply { add(ultimaCarta) }
                }
                jugador2haSacado = true
                controlarTurno(
                    cambiarBoton1Estado = {
                        boton1Estado = false
                    },
                    cambiarBoton2Estado = {
                        boton2Estado = false
                    },
                    jugador1haSacado,
                    jugador2haSacado,
                    reiniciarBotones = {
                        boton1Estado = true
                        boton2Estado = true
                    }
                )

            },
            boton2Estado = boton2Estado,
            onClickPasar = {

            }
        )
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
        TextoValor(valorJugador2, cambiarEstadoJugador = {
            if (valorJugador2 >= 21) {
                jugador2haSacado = false
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
fun TextoJugador(number: Int) {
    Text(
        text = "Jugador $number",
        fontSize = 25.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun RowBotonesJugador1(
    onClickPedir: () -> Unit, boton1Estado: Boolean,
    onClickPasar: () -> Unit,
) {
    Row(
        Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            shape = RectangleShape, onClick = {
                onClickPedir()
            }, colors = ButtonDefaults.buttonColors(Color.Red), enabled = boton1Estado
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
            enabled = boton1Estado
        ) {
            Text(
                text = "Pasar", textAlign = TextAlign.Center, color = Color.Black
            )
        }
    }
}

@Composable
fun RowBotonesJugador2(
    onClickPedir: () -> Unit, boton2Estado: Boolean,
    onClickPasar: () -> Unit
) {

    Row(
        Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            shape = RectangleShape, onClick = {
                onClickPedir()
            }, colors = ButtonDefaults.buttonColors(Color.Red), enabled = boton2Estado
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
            enabled = boton2Estado
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

fun controlarTurno(
    cambiarBoton1Estado: () -> Unit,
    cambiarBoton2Estado: () -> Unit,
    jugador1haSacado: Boolean,
    jugador2haSacado: Boolean,
    reiniciarBotones : () -> Unit

) {

    if (jugador1haSacado && !jugador2haSacado) {
        cambiarBoton1Estado()
    }
    if (jugador2haSacado && !jugador1haSacado) {
        cambiarBoton2Estado()
    }
    if (jugador1haSacado && jugador2haSacado) {
        reiniciarBotones()
    }

}

