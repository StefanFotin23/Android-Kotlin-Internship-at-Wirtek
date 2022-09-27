package com.example.wirtalks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.wirtalks.R

class PlaylistActivity : AppCompatActivity() {

    lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        setBackButton()

    }

    fun setBackButton(){

        backButton=findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}