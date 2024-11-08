package com.codesui.wallpaperapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codesui.footballfixtures.resources.Routes
import kotlinx.coroutines.delay

@Composable
fun StartScreen(navController: NavController, runAds: () -> Unit) {
    var isNavigated by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(5000)
        isNavigated = true
        runAds.invoke()
    }

    if (isNavigated) {
        navController.navigate(Routes.mainScreen)
    } else {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.w1),
                    contentScale = ContentScale.FillBounds
                ),
        ){

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "THIS ACTION MAY CONTAIN AD",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(Color.Transparent)
                        .wrapContentHeight(Alignment.CenterVertically),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(20.dp))
                LoadingAnimation()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
