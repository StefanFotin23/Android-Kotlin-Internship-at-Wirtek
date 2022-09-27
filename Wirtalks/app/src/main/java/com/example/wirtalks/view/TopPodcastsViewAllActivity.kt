package com.example.wirtalks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.example.wirtalks.R

class TopPodcastsViewAllActivity : AppCompatActivity() {
    lateinit var topPodcastTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_podcasts_view_all)


        fetchTopPodcastTitle()
        setBackButton()

    }

    fun setBackButton(){
        val backButton : ImageView = findViewById(R.id.back_button_from_top_podcasts)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    fun fetchTopPodcastTitle(){
        topPodcastTitle=findViewById(R.id.title_podcast_channel)

        val categoryName=intent.getStringExtra("category_name")

        topPodcastTitle.text="Top podcasts in  ${categoryName}"

    }
}