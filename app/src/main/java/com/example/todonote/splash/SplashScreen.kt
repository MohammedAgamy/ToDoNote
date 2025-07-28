
package com.example.todonote.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todonote.R
import com.example.todonote.presentation.screens.Routes
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navHostController: NavHostController) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animnte)
    )


    LaunchedEffect(true) {
        delay(3000)
        navHostController.navigate(Routes.HOME) {
            popUpTo("SplashScreen") { inclusive = true }

        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            alignment = Alignment.Center,
            modifier = Modifier.size(120.dp),
            composition = composition
        )
    }


}
