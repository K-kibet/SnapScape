package com.codesui.wallpaperapp

import android.app.WallpaperManager
import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun showWallpaperOptionsDialog(context: Context, imageUrl: String, showDialog: Boolean, onMutableValueChange: (Boolean) -> Unit, runAds: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onMutableValueChange(false)},
                    title = { Text("Set Wallpaper") },
            text = { Text("Choose where to set this wallpaper:") },
            confirmButton = {
                Button(onClick = {
                    setWallpaper(context, imageUrl, WallpaperManager.FLAG_SYSTEM)
                    onMutableValueChange(false)
                    runAds.invoke()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x4D000000),
                        contentColor = Color.White
                    ),
                    shape = RectangleShape,) {
                    Text("Home Screen")
                }
            },
            dismissButton = {
                Button(onClick = {
                    setWallpaper(context, imageUrl, WallpaperManager.FLAG_LOCK)
                    onMutableValueChange(false)
                    runAds.invoke()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0x4D000000),
                        contentColor = Color.White
                    ),
                    shape = RectangleShape,) {
                    Text("Lock Screen")
                }
            },



            /*neutralButton = {
                Button(onClick = {
                    setWallpaper(context, imageUrl, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                    showDialog = false
                }) {
                    Text("Both")
                }
            }*/
        )
    }
}
