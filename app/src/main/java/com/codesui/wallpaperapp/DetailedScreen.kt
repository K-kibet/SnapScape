package com.codesui.wallpaperapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.codesui.wallpaperapp.ads.AdmobBanner
import com.google.gson.JsonObject

@Composable
fun DetailedScreen (navController: NavController, runAds: () -> Unit,  photo: JsonObject) {
    val context = LocalContext.current
    var url by remember { mutableStateOf("https://images.pexels.com/photos/2880507/pexels-photo-2880507.jpeg") }
    var photographer by remember { mutableStateOf("Set Wallpaper") }

    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        url = photo.getAsJsonObject("src").get("large2x").asString
        photographer = photo.get("photographer").asString
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(data = url),
            contentDescription = photographer,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back Icon",
            modifier = Modifier
                .padding(5.dp)
                .size(45.dp)
                .align(alignment = Alignment.TopStart)
                .padding(5.dp)
                .background(color = Color(0x4D000000))
                .clickable {
                    navController.popBackStack()
                    runAds.invoke()
                },
            tint = Color.White,

        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x4D000000),
                        contentColor = Color.White
                    ),
                    shape = RectangleShape,
                ) {
                    Text(
                        text = "Set Wallpaper"
                    )
                }

                if (showDialog) {
                    showWallpaperOptionsDialog(
                        context,
                        url,
                        showDialog,
                        onMutableValueChange = { showDialog = it },
                        runAds)
                }

                Button(
                    onClick = { downloadImage(context, url) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x4D000000),
                        contentColor = Color.White
                    ),
                    shape = RectangleShape,
                ) {
                    Text("Download")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_file_download_24),
                        contentDescription = "Download",
                        modifier = Modifier,
                        tint = Color.White // You can change the color here
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            AdmobBanner()
        }
    }
}