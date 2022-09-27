package com.example.wirtalks.view

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.wirtalks.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class PodcastEpisodePlayerActivity : AppCompatActivity() {

     lateinit var titleEpisode: TextView
     lateinit var imageEpisode: ImageView
     lateinit var pubDate: TextView
     lateinit var  publisherName: TextView
     lateinit var backButtonFromEpisodePlayer: ImageView
     lateinit var episodeDuration: TextView
     lateinit var runnable: Runnable
     var handler= Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podcast_episode_player)

        val resTitleEpisode = intent.getStringExtra("episodeTitle")
        val resPubDate = intent.getLongExtra("episodePubDate", 12312312)
        val resAudioLink = intent.getStringExtra("episodeAudioLink")
        val resDuration = intent.getIntExtra("episodeDuration", 342)
        val resThumbnail = intent.getStringExtra("episodeThumbnail")
        var resEpId = intent.getStringExtra("episodeId")
        val publisher = intent.getStringExtra("publisher")


        val playButton = findViewById<ImageView>(R.id.player_button)
        val seekBar = findViewById<SeekBar>(R.id.seek_bar)
        val progressLoader = findViewById<ProgressBar>(R.id.progress_loader)
        val timeGone = findViewById<TextView>(R.id.time_gone)

        titleEpisode = findViewById(R.id.title_episode)
        pubDate = findViewById(R.id.date_episode)
        imageEpisode = findViewById(R.id.podcast_player_image)
        episodeDuration= findViewById(R.id.total_time)
        publisherName = findViewById(R.id.podcast_publisher)

        titleEpisode.text = resTitleEpisode
        pubDate.text = msToTimeDate(resPubDate)
        publisherName.text= publisher

        Picasso.with(this).load(resThumbnail)
            .into(imageEpisode)

        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setDataSource(resAudioLink)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            progressLoader.isGone = true
            playButton.isVisible = true
            seekBar.max = it.duration
        }
        seekBar.progress = 0

        playButton.setOnClickListener {
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                playButton.setImageResource(R.drawable.pausebutton)

            }else{
                mediaPlayer.pause()
                playButton.setImageResource(R.drawable.playbuttonplayer)
            }
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    mediaPlayer.seekTo(pos)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val isOverOneHour = resDuration / 3600 > 0
        var dateFormatter = "mm:ss"
        if (isOverOneHour) {
            dateFormatter = "HH:mm:ss"
        }
        val simpleDateFormat = SimpleDateFormat(dateFormatter)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")

        episodeDuration.text= simpleDateFormat.format(resDuration * 1000)

        runnable = Runnable {
            val dateString = simpleDateFormat.format(mediaPlayer.currentPosition)
            timeGone.text = dateString
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
        }
        runnable.run()
        handler.postDelayed(runnable, 1000)
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.playbuttonplayer)
            seekBar.progress = 0
        }


        backButtonFromEpisodePlayer=findViewById(R.id.back_button_from_episode_player)
        backButtonFromEpisodePlayer.setOnClickListener {
            onBackPressed()
            mediaPlayer.stop()
        }
      //  backButtonFromPlayer()
    }

    private fun msToTimeDate(ms: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(ms)
    }
}