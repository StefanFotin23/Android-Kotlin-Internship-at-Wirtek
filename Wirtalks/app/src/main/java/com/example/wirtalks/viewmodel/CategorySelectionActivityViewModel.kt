package com.example.wirtalks.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.wirtalks.model.GenresNetworkEntity
import com.example.wirtalks.services.CategoriesService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategorySelectionActivityViewModel:ViewModel() {
    var responseFromApi: List<GenresNetworkEntity> = emptyList()
    var selectedCategoryId: Int? = null
    var selectedCategoryName: String? = null

    fun fetchGenres(listener:GenresListener) {

        CoroutineScope(Dispatchers.IO).launch {
            responseFromApi = getDataFromApi()
                CoroutineScope(Dispatchers.Main).launch {
                    listener.onComplete(responseFromApi)
                }
        }
    }


    suspend fun getDataFromApi(): List<GenresNetworkEntity> {

        val service = Retrofit.Builder()
            .baseUrl("https://listen-api.listennotes.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(CategoriesService::class.java)

        val response = service.genres(
            Key="5812e09692494b5ea578590d09eb0bba",
            topLevelOnly = 1

        )

        return response.genres
    }

    interface GenresListener{
        fun onComplete(gen: List<GenresNetworkEntity>)
    }

}