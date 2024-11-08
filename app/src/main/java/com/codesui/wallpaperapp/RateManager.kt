package com.codesui.wallpaperapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class RateManager {
    fun rateApp(context: Context) {
        val packageName = context.packageName
        val uri = Uri.parse("market://details?id=$packageName")
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "Impossible to find an application for the market", Toast.LENGTH_LONG).show()
        }
    }
}