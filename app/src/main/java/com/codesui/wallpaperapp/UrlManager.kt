package com.codesui.wallpaperapp

import android.content.Context
import android.content.Intent
import android.net.Uri

fun UrlManager (url:String, context: Context) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url))
    context.startActivity(urlIntent)
}