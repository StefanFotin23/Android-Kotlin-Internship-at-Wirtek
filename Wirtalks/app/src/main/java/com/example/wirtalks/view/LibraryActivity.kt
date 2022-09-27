package com.example.wirtalks.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wirtalks.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class LibraryActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        // for debug purposes only => show the saved favorite categories in Shared Preference
        Log.d("CATEGORIES LIST", favoriteCategoriesList.toString())
        for (genre in favoriteCategoriesList)
            Log.d("CATEGORIES", genre.name)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //Navigation
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navhost_container) as NavHostFragment

        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)

        setupWithNavController(bottomNavigationView, navController)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.navHomeFragment, R.id.newFragment, R.id.profileFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

    }
}


