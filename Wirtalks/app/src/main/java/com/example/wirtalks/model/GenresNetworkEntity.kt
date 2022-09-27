package com.example.wirtalks.model

import com.google.gson.annotations.SerializedName

class GenresNetworkEntity(

    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("parent_id")
    var parent_id: Int

)