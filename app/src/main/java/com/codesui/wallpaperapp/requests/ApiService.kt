package com.codesui.wallpaperapp.requests

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET("v1/search")
    suspend fun getCuratedPhotos(
        @Query("query") query: String = "nature",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 120,
    ): JsonObject
}