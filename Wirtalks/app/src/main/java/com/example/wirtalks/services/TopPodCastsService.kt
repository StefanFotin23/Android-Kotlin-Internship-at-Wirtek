package com.example.wirtalks.services

import android.util.Log
import com.example.wirtalks.model.BestPodcasts
import com.example.wirtalks.model.CategoriesPodcast
import com.listennotes.podcast_api.Client
import com.listennotes.podcast_api.exception.AuthenticationException
import com.listennotes.podcast_api.exception.ListenApiException
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TopPodCastsService {


    @GET("best_podcasts")
    suspend fun top_podcasts(
        @Header("X-ListenAPI-Key") key: String,
         @Query("genre_id") genre_id:String
//        @Query("page") page:Int,
//        @Query("region") region:String,
//        @Query("sort")sort:String,
//        @Query("safe_mode") safe_mode : String
    ): BestPodcasts


}

