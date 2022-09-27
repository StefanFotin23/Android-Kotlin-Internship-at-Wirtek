package com.example.wirtalks.view

import android.content.Context
import android.content.SharedPreferences
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wirtalks.R
import com.example.wirtalks.adapters.PodcastEpisodesAdapter
import com.example.wirtalks.model.PodcastEpisode
import com.example.wirtalks.model.PodcastEpisodeNetworkEntity
import com.example.wirtalks.viewmodel.PodcastEpisodesViewModel
import com.squareup.picasso.Picasso

class PodcastChannelActivity : AppCompatActivity() {

    // This Data must be sent by BL to this Activity
    lateinit var channelImageLink: String
    lateinit var channelName: String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var viewModelForEpisodes: PodcastEpisodesViewModel
    lateinit var podcastId: String
    lateinit var podcastPublisher: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast_channel)

        //Getting data from LibraryFragment
        podcastId = intent.getStringExtra("podcastId") ?: "4d3fe717742d4963a85562e9f84d8c79"
        channelName = intent.getStringExtra("podcastTitle") ?: "Name"
        channelImageLink = intent.getStringExtra("podcastImage") ?: "Image"
        podcastPublisher = intent.getStringExtra("podcastPublisher") ?: "Publisher"

        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        var backButton: ImageView = findViewById(R.id.podcastChannelBackButton)
        var optionsButton: ImageView = findViewById(R.id.optionsButton) // No functionality given
        var channelImage: ImageView = findViewById<ImageView>(R.id.ChannelImage)
        var podcastChannelName: TextView = findViewById(R.id.PodcastName)
        var favoriteButton: ImageView =
            findViewById(R.id.favoriteButton) // No functionality, just UI


        apiCallForEpisodes()

        // Button going back to last activity
        backButton.setOnClickListener {
            onBackPressed()
        }

        favoriteButton.setOnClickListener {
            // not implemented yet
        }

        optionsButton.setOnClickListener {
            // not implemented yet
        }

        // Podcast Image is loaded in the design layout
        Picasso.with(this).load(channelImageLink)
            .into(channelImage)

        // Podcast Channel name loaded
        podcastChannelName.text = channelName

        apiCallForEpisodes()
    }

    private fun apiCallForEpisodes() {
        viewModelForEpisodes = PodcastEpisodesViewModel()


        viewModelForEpisodes.fetchPodcasts(
            podcastId,
            object : PodcastEpisodesViewModel.PodcastEpisodesListener {
                override fun onComplete(podcastEpisode: PodcastEpisode?) {
                    val PodcastPublisher: TextView = findViewById(R.id.Publisher)
                    if (podcastEpisode != null) {
                        PodcastPublisher.text=podcastEpisode.publisher
                    }

                    val recyclerView: RecyclerView = findViewById(R.id.podcastPlaylistRecyclerView)

                    //RecycleView Part Start
                    recyclerView.layoutManager = LinearLayoutManager(this@PodcastChannelActivity)

                    // This will pass the ArrayList to our Adapter
                    val adapter = PodcastEpisodesAdapter(podcastEpisode?.episodes ?: listOf())

                    // Setting the Adapter with the recyclerview
                    recyclerView.adapter = adapter
                    adapter.setOnIemClickListener(object : PodcastEpisodesAdapter.onItemClickListener {
                        override fun onItemClick(position: Int,episode:PodcastEpisodeNetworkEntity) {
                            val navigateToPlayerActivity = Intent(this@PodcastChannelActivity, PodcastEpisodePlayerActivity::class.java)
                            //Send data to PodcastEpisodePlayerActivity
                            navigateToPlayerActivity.putExtra("episodeTitle",episode.title)
                            navigateToPlayerActivity.putExtra("episodePubDate",episode.pub_date_ms)
                            navigateToPlayerActivity.putExtra("episodeAudioLink",episode.audio)
                            navigateToPlayerActivity.putExtra("episodeDuration",episode.audio_length_sec)
                            navigateToPlayerActivity.putExtra("episodeThumbnail",episode.image)
                            navigateToPlayerActivity.putExtra("episodeId",episode.idEp)
                            navigateToPlayerActivity.putExtra("publisher", podcastEpisode?.publisher)
                            startActivity(navigateToPlayerActivity)
                        }

                    })

                }
            })
    }


}