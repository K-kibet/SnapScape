package com.codesui.wallpaperapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codesui.footballfixtures.resources.Routes
import com.google.gson.Gson
import com.google.gson.JsonObject

@Composable
fun AppNavigation(runAds :() -> Unit, openAds: () -> Unit, rewardedAds : () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.startScreen, builder ={
        composable(Routes.startScreen) {
            StartScreen(navController, runAds)
        }
        composable(Routes.mainScreen) {
            NavDrawer(navController, runAds, openAds, rewardedAds)
        }
        composable(Routes.detailedScreen + "/{jsonObject}") { backStackEntry ->
            val gson = Gson()
            val jsonString = backStackEntry.arguments?.getString("jsonObject")
            val jsonObject: JsonObject = gson.fromJson(jsonString, JsonObject::class.java)
            DetailedScreen(navController = navController, runAds = rewardedAds, photo = jsonObject)
        }
    } )
}