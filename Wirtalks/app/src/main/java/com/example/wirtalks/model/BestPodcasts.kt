package com.example.wirtalks.model

import com.google.gson.annotations.SerializedName

class BestPodcasts (
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    var name : String,
    @SerializedName("podcasts")
    var  podcasts : List<PodcastNetworkEntity>,
    @SerializedName("parent_id")
    var parent_id : Int

)
