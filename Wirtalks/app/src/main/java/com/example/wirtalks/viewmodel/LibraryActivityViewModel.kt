package com.example.wirtalks.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wirtalks.model.PodcastNetworkEntity
import com.example.wirtalks.services.TopPodCastsService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.Dispatchers

class LibraryActivityViewModel : ViewModel() {

    var podcastResponseFromApi: List<PodcastNetworkEntity> = emptyList()

    fun fetchPodcasts(categoryId: String = "77",listener:PodcastListener) {
        CoroutineScope(IO).launch {
            podcastResponseFromApi = getPodcastsFromApi(categoryId)
            CoroutineScope(Dispatchers.Main).launch {
                listener.onComplete(podcastResponseFromApi)
            }
        }
    }

    suspend fun getPodcastsFromApi(categoryId: String): List<PodcastNetworkEntity> {
        val service = Retrofit.Builder()
            .baseUrl("https://listen-api.listennotes.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(TopPodCastsService::class.java)

        val response = service.top_podcasts(
            key = "d1e087aab16e4e8ab86a574fb4f22ab9",
            genre_id = categoryId
        )
        return response.podcasts
    }


interface PodcastListener{
    fun onComplete(podcast:List<PodcastNetworkEntity>)
}

}