package com.codesui.wallpaperapp.ads

import android.app.Activity
import android.content.Context
import com.codesui.wallpaperapp.R
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

class RewardedInterstitialManager(var activity: Activity, var context: Context) :
    OnUserEarnedRewardListener {
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    fun loadAd() {
        RewardedInterstitialAd.load(
            activity, activity.getString(R.string.Rewarded_Ad_Id),
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad
                    rewardedInterstitialAd!!.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdClicked() {
                                // Called when a click is recorded for an ad.
                                //Log.d(TAG, "Ad was clicked.");
                            }

                            override fun onAdDismissedFullScreenContent() {
                                rewardedInterstitialAd = null
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when ad fails to show.
                                //Log.e(TAG, "Ad failed to show fullscreen content.");
                                rewardedInterstitialAd = null
                            }

                            override fun onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                //Log.d(TAG, "Ad recorded an impression.");
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                            }
                        }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    //Log.d(TAG, loadAdError.toString());
                    rewardedInterstitialAd = null
                }
            })
    }

    fun showAdNow() {
        if (rewardedInterstitialAd != null) {
            rewardedInterstitialAd!!.show(
                activity
            ) { rewardItem: RewardItem ->
                onUserEarnedReward(
                    rewardItem
                )
            }
        } else {
            //loadAd()
        }
    }
    override fun onUserEarnedReward(rewardItem: RewardItem) {
    }
}
