package com.example.wirtalks.services

import com.example.wirtalks.model.CategoriesPodcast
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CategoriesService {

    @GET("genres")
    suspend fun genres(
        @Header("X-ListenAPI-Key") Key:String,
        @Query("top_level_only") topLevelOnly:Int
    ): CategoriesPodcast

}