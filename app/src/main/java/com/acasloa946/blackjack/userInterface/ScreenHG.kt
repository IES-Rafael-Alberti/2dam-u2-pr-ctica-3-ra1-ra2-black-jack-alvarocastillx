package com.acasloa946.blackjack.userInterface

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acasloa946.blackjack.R
import com.acasloa946.blackjack.data.Carta

@Composable
fun ScreenHG(NavController: NavController, hgViewmodel: HGViewmodel) {
        val imagenID: Int by hgViewmodel.imagenID.observeAsState(initial = R.drawable.facedown)
        val cartaNueva: Carta by hgViewmodel.cartaNueva.observeAsState(initial = Carta())




    //Columna principal
        Column(
            Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.mat),
                    contentScale = ContentScale.FillWidth
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Imagen de la baraja con el texto de las cartas que quedan si la imagen que se esta mostrando no es la carta boca abajo.
            if (imagenID != R.drawable.facedown) {
                ImagenBaraja(hgViewmodel.baraja.listaCartas.size)
            }
            //Inicia función crearImagen (Imagen de la carta)
            CrearImagen(imagenID,cartaNueva)
            //Row para los botones
            Row(
                modifier = Modifier.padding(top = 30.dp)
            ) {
                //Funcion para los botones
                Botones(hgViewmodel)
            }

        }
    }

    /**
     * Función para mostrar una imagen de la baraja con un texto mostrando las cartas que quedan.
     * Solo se entra cuando la imagen que se está mostrando es la carta boca abajo.
     */
    @Composable
    fun ImagenBaraja(numb: Int) {
        Box(
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.facedown),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 50.dp)
            )
            Text(
                text = "$numb",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.background(Color.Black)
            )
        }

    }

    /**
     * Función que crea la imagen de la carta
     * @param imagenID -> id de la imagen para representarla en el painterResource
     * @param Carta -> carta actual para el texto que muestra su nombre y palo
     */
    @Composable
    fun CrearImagen(imagenID: Int, Carta: Carta) {
        if (imagenID!=R.drawable.facedown) {
            Text(
                text = "${Carta.Nombre} de ${Carta.Palo}",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
        }
        Image(
            painter = painterResource(id = imagenID),
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)
        )
    }


@Composable

fun Botones(hgViewmodel: HGViewmodel) {
    Button(
        onClick = {
            hgViewmodel.onClickDameCarta()
        },
        modifier = Modifier.padding(end = 5.dp),
        colors = ButtonDefaults.buttonColors(Color.Red)
    ) {
        Text(
            text = "Dame carta",
            color = Color.Black
        )
    }
    Button(
        onClick = { hgViewmodel.onClickReiniciar() },
        colors = ButtonDefaults.buttonColors(Color.Red)
    ) {
        Text(
            text = "Reiniciar",
            color = Color.Black
        )
    }
}
