package com.example.wirtalks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wirtalks.PodcastViewHolder
import com.example.wirtalks.R
import com.example.wirtalks.model.PodcastNetworkEntity

class PodcastAdapter(var podcast : List<PodcastNetworkEntity>) : RecyclerView.Adapter<PodcastViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.activity_playlist,parent,false)
        return PodcastViewHolder(view)
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {

        holder.podcastTitle.text = podcast[position].title
    }

    override fun getItemCount(): Int {
return podcast.size    }


}