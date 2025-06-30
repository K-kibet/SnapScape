package com.codesui.wallpaperapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codesui.wallpaperapp.ads.AppOpenAdManager
import com.codesui.wallpaperapp.ads.RewardedInterstitialManager
import com.codesui.wallpaperapp.ui.theme.WallpaperAppTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.onesignal.OneSignal
import kotlinx.coroutines.runBlocking


class MainActivity : ComponentActivity() {
    private lateinit var appOpenAdManager: AppOpenAdManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            installSplashScreen()
            MobileAds.initialize(this@MainActivity) {}
            // OneSignal Initialization
            OneSignal.initWithContext(this@MainActivity)
            OneSignal.initWithContext(this@MainActivity, this@MainActivity.getString(R.string.one_signal_id))
        }
        appOpenAdManager = AppOpenAdManager()
        appOpenAdManager.loadAd(this@MainActivity)
        setContent {
            WallpaperAppTheme {
                val rewardedInterstitialManager = RewardedInterstitialManager(context = LocalContext.current, activity =  this@MainActivity)
                rewardedInterstitialManager.loadAd()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation({ showInterstialAd() }, {
                        appOpenAdManager.showAdIfAvailable(this@MainActivity)
                    }, {
                        rewardedInterstitialManager.loadAd()
                        rewardedInterstitialManager.showAdNow()
                    })
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        appOpenAdManager.showAdIfAvailable(this@MainActivity)
    }

    override fun onStart() {
        super.onStart()
        appOpenAdManager.showAdIfAvailable(this@MainActivity)
    }

    override fun onRestart() {
        super.onRestart()
        super.onStart()
        appOpenAdManager.showAdIfAvailable(this@MainActivity)
    }

    private fun showInterstialAd() {
        InterstitialAd.load(
            this,
            getString(R.string.Interstitial_Ad_Unit), //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(this@MainActivity)
                }
            }
        )
    }
}
