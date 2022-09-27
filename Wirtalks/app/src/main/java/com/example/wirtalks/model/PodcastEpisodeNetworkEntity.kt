package com.example.wirtalks.model

import com.google.gson.annotations.SerializedName

class PodcastEpisodeNetworkEntity (
    @SerializedName("id")
   var idEp: String,
    @SerializedName("audio")
    var audio: String,
     @SerializedName("title")
    var title: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("pub_date_ms")
     var pub_date_ms: Long,
     @SerializedName("audio_length_sec")
    var audio_length_sec: Int,
    @SerializedName("thumbnail")
    var image: String
         )
