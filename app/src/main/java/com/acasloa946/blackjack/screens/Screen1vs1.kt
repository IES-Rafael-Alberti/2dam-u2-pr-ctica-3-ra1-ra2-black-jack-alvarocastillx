package com.acasloa946.blackjack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.acasloa946.blackjack.routes.Routes

var carta1jugador1 = Carta()
var carta2jugador1 = Carta()

var carta1jugador2 = Carta()
var carta2jugador2 = Carta()

@Composable
fun Pantalla1vs1(NavController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.mat),
                contentScale = ContentScale.FillHeight
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextoJugador(number = 1)
        RowBotonesJugador1()

        Row {
            val barajaJugador1 = Jugador()
            barajaJugador1.crearBaraja()
            barajaJugador1.barajar()
            carta1jugador1 = barajaJugador1.dameCarta()
            carta2jugador1 = barajaJugador1.dameCarta()
            ImagenJugador1(carta1jugador1.IdDrawable)
            ImagenJugador1(carta2jugador1.IdDrawable)

        }
        TextoValor(1)
        Spacer(modifier = Modifier.padding(15.dp))
        TextoJugador(number = 2)
        RowBotonesJugador2()
        Row {
            val barajaJugador2 = Jugador()
            val manoJugador2 = barajaJugador2.mano
            barajaJugador2.crearBaraja()
            barajaJugador2.barajar()
            carta1jugador2 = barajaJugador2.dameCarta()
            carta2jugador2 = barajaJugador2.dameCarta()
            ImagenJugador2(carta1jugador2.IdDrawable)
            ImagenJugador2(carta2jugador2.IdDrawable)
        }
        TextoValor(numb = 2)
    }
}

@Composable
fun ImagenJugador1(foto: Int) {
    Image(
        painter = painterResource(id = foto),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
    )
}

@Composable
fun ImagenJugador2(foto: Int) {
    Image(
        painter = painterResource(id = foto),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
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
fun RowBotonesJugador1() {
    Row (
        Modifier.padding(bottom = 10.dp)
    ){
        Button(
            shape = RectangleShape,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "Pedir",
                textAlign = TextAlign.Center,
                color = Color.Black
            )

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            shape = RectangleShape,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "Pasar",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
fun RowBotonesJugador2() {
    Row (
        Modifier.padding(bottom = 10.dp)
    ){
        Button(
            shape = RectangleShape,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "Pedir",
                textAlign = TextAlign.Center,
                color = Color.Black
            )

        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            shape = RectangleShape,
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "Pasar",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
fun TextoValor(numb:Int) {
    val valor1 = carta1jugador1.PuntosMax+ carta2jugador1.PuntosMax
    val valor2 = carta1jugador2.PuntosMax+ carta2jugador2.PuntosMax

    if (numb == 1) {
        Text(
            text = "Valor: $valor1",
            fontSize = 25.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
    else {
        Text(
            text = "Valor: $valor2",
            fontSize = 25.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(top = 10.dp)
        )
    }

}