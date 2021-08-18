package id.kitabeli.mitra.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import id.kitabeli.mitra.R
import id.kitabeli.mitra.navigation.Screens
import kotlinx.coroutines.*


@Composable
fun Splash(navController: NavController) {


    LaunchedEffect(true) {
        MainScope().launch {
            delay(2000L)
            navController.popBackStack()
            navController.navigate(Screens.Login.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = "Splash Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

    }

}






