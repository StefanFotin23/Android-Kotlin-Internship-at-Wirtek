package com.example.wirtalks

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PodcastViewHolder(itemPodcast : View) : RecyclerView.ViewHolder(itemPodcast) {

     var play =itemPodcast.findViewById<ImageView>(R.id.play_podcast)
     var podcastTitle=itemPodcast.findViewById<TextView>(R.id.podcast_title)
     var podcastDetail=itemPodcast.findViewById<TextView>(R.id.podcast_episode)
     var timeLeft=itemPodcast.findViewById<TextView>(R.id.time_left)


}