package com.example.wirtalks.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.wirtalks.R
import com.example.wirtalks.model.GenresNetworkEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

var newUser: Boolean = true
var favoriteCategoriesList = arrayListOf<GenresNetworkEntity>()
// the list where we will save the available podcast categories

class SplashScreenActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        newUser = sharedPreferences.getBoolean("key newUser", true)
        favoriteCategoriesList = getFavoriteCategoriesList()
        // we get the podcast categories list as we first start the app
        // if the user is new, the list is empty
        // else we get the correct list

        supportActionBar?.hide()
        Handler().postDelayed({
            if (newUser) {
                val favoriteCategories = Intent(this, CategorySelectionActivity::class.java)
                startActivity(favoriteCategories)
            } else {
                val libraryActivity = Intent(this@SplashScreenActivity, LibraryActivity::class.java)
                startActivity(libraryActivity)
            }
            finish()
        }, 1000)
    }

    // returns the list of genres (name, ID, parent ID)
    private fun getFavoriteCategoriesList():ArrayList<GenresNetworkEntity>{
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("key favoriteCategories",null)
        lateinit var list: ArrayList<GenresNetworkEntity>

        val gson = Gson()
        val type = object : TypeToken<ArrayList<GenresNetworkEntity>>(){}.type//converting the json to list

        //if list == null => return empty list
        if (json == null) {
            list = arrayListOf<GenresNetworkEntity>()
            list.clear()
        } else {
            list = gson.fromJson(json, type)
        }

        return list//returning the list
    }
}