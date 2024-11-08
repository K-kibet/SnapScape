package com.codesui.wallpaperapp
import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun saveInteger(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getInteger(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }
}