package com.codesui.footballfixtures.Requests

import com.codesui.wallpaperapp.requests.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.pexels.com/"
    private val gson: Gson = GsonBuilder().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(
            OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "563492ad6f91700001000001bc9ff543fb19405fb6dbc60e03d0440f")
                    .build()
                chain.proceed(request)
            }.build()
        )
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}