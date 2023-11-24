package com.acasloa946.blackjack.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acasloa946.blackjack.R
import com.acasloa946.blackjack.routes.Routes


@Composable
fun PantallaModo(NavController: NavController) {
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
        TextoTitulo()
        Button(
            shape = RectangleShape,
            onClick = { NavController.navigate(Routes.Pantalla1vs1.route) },
            modifier = Modifier
                .size(height = 150.dp, width = 150.dp)
                .padding(bottom = 30.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "1 VS 1",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        Button(
            shape = RectangleShape,
            onClick = { NavController.navigate(Routes.Pantalla1vsIA.route) },
            modifier = Modifier
                .size(height = 130.dp, width = 150.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text(
                text = "1 vs IA.",
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
fun TextoTitulo() {
    Text(text = "BlackJack",
        fontSize = 40.sp,
        fontWeight = FontWeight.W500,
        fontFamily = FontFamily.Monospace)
    Image(painter = painterResource(id = R.drawable.blackjack),
        contentDescription = null,
        modifier = Modifier.padding(bottom = 75.dp))
}
