package com.codesui.wallpaperapp.ads

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.codesui.wallpaperapp.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            AdView(ctx).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = ctx.getString(R.string.Banner_Ad_Unit)
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}