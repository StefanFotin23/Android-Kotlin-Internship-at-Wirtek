package com.example.wirtalks.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wirtalks.model.PodcastEpisode
import com.example.wirtalks.model.PodcastEpisodeNetworkEntity
import com.example.wirtalks.services.PodcastEpisodesService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PodcastEpisodesViewModel : ViewModel() {

    var podcastEpisodesResponseFromApi: PodcastEpisode? = null

    fun fetchPodcasts(podcastId: String, listener: PodcastEpisodesListener) {
        CoroutineScope(Dispatchers.IO).launch {
            podcastEpisodesResponseFromApi = getPodcastsEpisodeFromApi(podcastId)
            CoroutineScope(Main).launch {
                listener.onComplete(podcastEpisodesResponseFromApi)
            }

        }
    }

    suspend fun getPodcastsEpisodeFromApi(podcastId: String): PodcastEpisode {
        val service = Retrofit.Builder()
            .baseUrl("https://listen-api.listennotes.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(PodcastEpisodesService::class.java)

        val response = service.podcasts(
            podcastId = podcastId,
            key = "5812e09692494b5ea578590d09eb0bba",
        )
        return response
    }



    interface PodcastEpisodesListener {
        fun onComplete(podcastEpisode: PodcastEpisode?)
    }


}