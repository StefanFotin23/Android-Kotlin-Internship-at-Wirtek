package com.example.wirtalks.services

import com.example.wirtalks.model.PodcastEpisode
import retrofit2.http.*

interface PodcastEpisodesService {

    @GET("podcasts/{podcastId}")
    suspend fun podcasts(
        @Path("podcastId") podcastId: String,
        @Header("X-ListenAPI-Key") key: String,
    ): PodcastEpisode

}