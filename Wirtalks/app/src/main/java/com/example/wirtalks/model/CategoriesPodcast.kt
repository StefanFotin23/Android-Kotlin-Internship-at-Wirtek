package com.example.wirtalks.model

import com.google.gson.annotations.SerializedName

class CategoriesPodcast (

    @SerializedName("genres")
    var genres : List<GenresNetworkEntity>
)
