package com.codesui.wallpaperapp

import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import coil.Coil
import coil.request.ImageRequest
import com.google.gson.JsonObject

fun downloadImage(context: Context, url: String) {
    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle("Wallpaper Download")
        .setDescription("Downloading...")
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "wallpaper.jpg")

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)

    Toast.makeText(context, "Download started...", Toast.LENGTH_SHORT).show()
}

fun setWallpaper(context: Context, imageUrl: String, flag: Int) {
    try {
        val wallpaperManager = WallpaperManager.getInstance(context)
        // Use an image loader (like Coil) to load the image bitmap and then set it
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .target { drawable ->
                val bitmap = (drawable as BitmapDrawable).bitmap
                wallpaperManager.setBitmap(bitmap, null, true, flag)
            }
            .build()

        // Assuming you have a Coil singleton ImageLoader initialized
        Coil.imageLoader(context).enqueue(request)
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}