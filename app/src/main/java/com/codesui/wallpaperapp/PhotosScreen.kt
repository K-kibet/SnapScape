package com.codesui.wallpaperapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.codesui.footballfixtures.Requests.RetrofitClient
import com.codesui.footballfixtures.resources.Routes
import com.codesui.footballfixtures.resources.isInternetAvailable
import com.codesui.wallpaperapp.ads.AdmobBanner
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PhotosScreen(navController: NavController, runAds: () ->Unit, rewardedAds: () -> Unit){
    val photos = remember { mutableStateOf<List<JsonObject>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    var isButtonClicked by remember { mutableStateOf(true) }
    LaunchedEffect(isButtonClicked) {
        if (isButtonClicked) {
            try {
                val response = RetrofitClient.apiService.getCuratedPhotos(page = 1)
                photos.value = response.getAsJsonArray("photos").map{ it.asJsonObject }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                isLoading.value = false
            }
            isButtonClicked = false // Reset state after task
        }
    }


    when {
        isLoading.value -> {
            IndeterminateCircularIndicator()
        }
        error.value != null -> {
            when{
                !isInternetAvailable(LocalContext.current) -> {
                    NoInternetDialog {
                        isButtonClicked = true
                        isLoading.value = true
                        error.value = null
                    }
                }
                isInternetAvailable(LocalContext.current) -> {
                    ErrorDialog()
                }
            }
        }

        photos.value != null -> {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(items = photos.value!!) { photo ->
                        PhotoItem(photo, navController,runAds)
                    }
                }
                AdmobBanner()
            }
        }
    }
}


@Composable
fun PhotoItem(photo : JsonObject, navController: NavController, runAds: () -> Unit) {
    val imageUrl = photo.getAsJsonObject("src").get("medium")?.asString
    val contentDescription = photo.get("url")?.asString ?: "Image"
    val context = LocalContext.current

    val gson = Gson()
    val jsonString = gson.toJson(photo)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .clickable {
                try {
                    val encodedJsonString =
                        URLEncoder.encode(jsonString, StandardCharsets.UTF_8.toString())
                    navController.navigate(Routes.detailedScreen + "/$encodedJsonString")
                    runAds.invoke()
                } catch (e: Exception) {
                    Toast
                        .makeText(context, "${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            },

    ) {
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}