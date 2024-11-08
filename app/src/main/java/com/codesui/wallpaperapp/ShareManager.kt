package com.codesui.wallpaperapp

import android.content.Context
import android.content.Intent

fun ShareManager(context: Context) {
    val sendIntent : Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, "Share App With Friends")
        val packageName = context.packageName
        putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=$packageName")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}