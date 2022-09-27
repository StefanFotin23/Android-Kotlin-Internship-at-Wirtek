package com.example.wirtalks.model

import com.google.gson.annotations.SerializedName

class PodcastNetworkEntity(
    @SerializedName("id")
    var id: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("thumbnail")
    var thumbnail: String,

    @SerializedName("title")
    var title: String

)