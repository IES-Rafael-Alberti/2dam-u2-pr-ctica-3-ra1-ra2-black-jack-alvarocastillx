package com.acasloa946.blackjack.userInterface

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
import androidx.compose.runtime.livedata.observeAsState
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
        TextoTurno(viewModel1vs1,refreshTxtTurno)
        RowBotonesJugadores(viewModel1vs1)
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        Text(
            text = "Jugador 1",
            fontSize = 25.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 30.dp),
            fontStyle = FontStyle.Italic
        )
        ManoJugador1(handP1, refreshPlayerCards)
        TextoValor(viewModel1vs1 = viewModel1vs1, player = 1)
        Text(
            text = "Jugador 2",
            fontSize = 25.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(top = 30.dp, bottom = 30.dp),
            fontStyle = FontStyle.Italic
        )
        ManoJugador2(handP2 = handP2, refreshPlayerCards = refreshPlayerCards)
        TextoValor(viewModel1vs1 = viewModel1vs1, player = 2)

    }
}


@Composable
fun ManoJugador1(handP1: List<Carta>, refreshPlayerCards: Boolean) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(-100.dp)
    ){
        items(handP1) {
            CrearImagen(foto = it.IdDrawable)
        }
    }
}

@Composable
fun ManoJugador2(handP2: List<Carta>, refreshPlayerCards: Boolean) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(-100.dp)
    ) {
        items(handP2) {
            CrearImagen(foto = it.IdDrawable)
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
        Modifier.padding(bottom = 10.dp)
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
fun TextoValor(viewModel1vs1:ViewModel1vs1,player:Int) {
    val valorJ1: Int by viewModel1vs1.valorJ1.observeAsState(initial = 0)
    val valorJ2: Int by viewModel1vs1.valorJ2.observeAsState(initial = 0)

    if (player == 1) {
        Text(
            text = "Valor: $valorJ1",
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
            fontStyle = FontStyle.Italic
        )
    }
    else {
        Text(
            text = "Valor: $valorJ2",
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Black,
            modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
            fontStyle = FontStyle.Italic
        )
    }

}
@Composable
fun TextoTurno(viewModel1vs1: ViewModel1vs1,refreshTxtTurno:Boolean) {
    Text(
        text = viewModel1vs1.controlTxtTurno(),
        fontSize = 20.sp,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
        fontStyle = FontStyle.Italic
    )
}




