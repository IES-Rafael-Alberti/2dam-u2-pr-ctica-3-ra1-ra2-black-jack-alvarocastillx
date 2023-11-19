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
import com.acasloa946.blackjack.Carta
import com.acasloa946.blackjack.Jugador
import com.acasloa946.blackjack.R

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Pantalla1vs1(NavController: NavController) {
    var jugador1Jugando by rememberSaveable { mutableStateOf(true) }
    var jugador2Jugando by rememberSaveable { mutableStateOf(true) }

    val barajaJugador1 = Jugador()
    var manoJugador1 by remember { mutableStateOf(barajaJugador1.mano) }
    var valorJugador1 by rememberSaveable { mutableStateOf(0) }

    val barajaJugador2 = Jugador()
    var manoJugador2 by remember { mutableStateOf(barajaJugador2.mano) }
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
                if (barajaJugador1.listaCartas.isEmpty()) {
                    barajaJugador1.crearBaraja()
                    barajaJugador1.barajar()
                    val ultimaCarta = barajaJugador1.dameCarta()
                    valorJugador1 += ultimaCarta.PuntosMax
                    manoJugador1 = manoJugador1.toMutableList().apply { add(ultimaCarta) }
                } else {
                    val ultimaCarta = barajaJugador1.dameCarta()
                    manoJugador1 = manoJugador1.toMutableList().apply { add(ultimaCarta) }
                }
                controlarTurno(
                    jugador1Jugando = {
                        jugador1Jugando = !jugador1Jugando
                    },
                    jugador2Jugando = {
                        jugador2Jugando = !jugador2Jugando
                    },
                    reiniciar = {
                        jugador1Jugando = true
                        jugador2Jugando = true
                    },
                    manoJugador1,
                    manoJugador2,
                    valorJugador1,
                    valorJugador2


                )
            }, jugador1Jugando = jugador1Jugando
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
                jugador1Jugando = false
            }
        })


        //jugador2
        Spacer(modifier = Modifier.padding(10.dp))
        TextoJugador(number = 2)
        RowBotonesJugador2(
            onClickPedir = {
                if (barajaJugador2.listaCartas.isEmpty()) {
                    barajaJugador2.crearBaraja()
                    barajaJugador2.barajar()
                    val ultimaCarta = barajaJugador2.dameCarta()
                    valorJugador2 += ultimaCarta.PuntosMax
                    manoJugador2 = manoJugador2.toMutableList().apply { add(ultimaCarta) }
                } else {
                    val ultimaCarta = barajaJugador2.dameCarta()
                    manoJugador2 = manoJugador2.toMutableList().apply { add(ultimaCarta) }
                }
                controlarTurno(
                    jugador1Jugando = {
                        jugador1Jugando = !jugador1Jugando
                    },
                    jugador2Jugando = {
                        jugador2Jugando = !jugador2Jugando
                    },
                    reiniciar = {
                        jugador1Jugando = true
                        jugador2Jugando = true
                    },
                    manoJugador1,
                    manoJugador2,
                    valorJugador1,
                    valorJugador2
                )
            },
            jugador2Jugando = jugador2Jugando
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
                jugador2Jugando = false
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
    onClickPedir: () -> Unit, jugador1Jugando: Boolean
) {

    Row(
        Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            shape = RectangleShape, onClick = {
                onClickPedir()
            }, colors = ButtonDefaults.buttonColors(Color.Red), enabled = jugador1Jugando
        ) {
            Text(
                text = "Pedir", textAlign = TextAlign.Center, color = Color.Black
            )

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            shape = RectangleShape,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red),
            enabled = jugador1Jugando
        ) {
            Text(
                text = "Pasar", textAlign = TextAlign.Center, color = Color.Black
            )
        }
    }
}

@Composable
fun RowBotonesJugador2(
    onClickPedir: () -> Unit, jugador2Jugando: Boolean
) {

    Row(
        Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            shape = RectangleShape, onClick = {
                onClickPedir()
            }, colors = ButtonDefaults.buttonColors(Color.Red), enabled = jugador2Jugando
        ) {
            Text(
                text = "Pedir", textAlign = TextAlign.Center, color = Color.Black
            )

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            shape = RectangleShape,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red),
            enabled = jugador2Jugando
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
    jugador1Jugando: () -> Unit,
    jugador2Jugando: () -> Unit,
    reiniciar: () -> Unit,
    manoJugador1: MutableList<Carta>,
    manoJugador2: MutableList<Carta>,
    valorJugador1 :Int,
    valorJugador2 : Int
) {
    if ((manoJugador1.size > manoJugador2.size) && (valorJugador1<=21 && valorJugador2<=21)) {
        jugador1Jugando()
    } else if (manoJugador2.size > manoJugador1.size && (valorJugador1<=21 && valorJugador2<=21)) {
        jugador2Jugando()
    } else if (manoJugador2.size == manoJugador1.size && (valorJugador1<=21 && valorJugador2<=21)) {
        reiniciar()
    }
}

