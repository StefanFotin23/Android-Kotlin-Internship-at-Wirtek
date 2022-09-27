package com.example.wirtalks.model

import com.google.gson.annotations.SerializedName

class PodcastEpisode (


        @SerializedName("id")
        var idPod: String,
        @SerializedName("image")
        var image: String,
        @SerializedName("title")
        var title: String,
        @SerializedName("publisher")
        var publisher: String,
        @SerializedName("thumbnail")
        var thumbnail: String,
        @SerializedName("episodes")
        var episodes: List<PodcastEpisodeNetworkEntity>


)