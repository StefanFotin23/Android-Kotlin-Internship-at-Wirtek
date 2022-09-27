package com.example.wirtalks.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wirtalks.R
import com.example.wirtalks.model.GenresNetworkEntity
import com.example.wirtalks.viewmodel.CategorySelectionActivityViewModel
import com.google.gson.Gson

    var availableCategories = arrayListOf<GenresNetworkEntity>()

class CategorySelectionActivity : AppCompatActivity() {

    lateinit var viewModel: CategorySelectionActivityViewModel
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection_category)

        viewModelFetchGenres()
        skipButton()
        nextButton()

    }

    private fun viewModelFetchGenres() {

        viewModel = CategorySelectionActivityViewModel()
        viewModel.fetchGenres(object : CategorySelectionActivityViewModel.GenresListener {
            override fun onComplete(gen: List<GenresNetworkEntity>) {
                generateCategoryButtons(gen)
            }
        })
    }

    //SHARED PREFERENCES method to save the available categories after fetch
    private fun saveCategories() {
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = gson.toJson(favoriteCategoriesList)
        //converting list to Json

        val editor = sharedPreferences.edit()
        newUser = false
        editor.putBoolean("key newUser", newUser)
        editor.putString("key favoriteCategories", json)
        editor.apply()
    }

    private fun specialClickListener(view: View, categoryId: Int, categoryName: String) {
        view.isSelected = !view.isSelected
        if (view.isSelected) {
            (view as Button).setTextColor(Color.WHITE)
            viewModel.selectedCategoryId = categoryId
            viewModel.selectedCategoryName = categoryName
            // we add the genre that is selected to the list
            favoriteCategoriesList.add(GenresNetworkEntity(categoryId, categoryName, 0))
        } else {
            (view as Button).setTextColor(Color.BLUE)
            // if the button is deselected, we remove that category from favorites
            removeCategoryFromList(view.text.toString())
        }
    }

    private fun removeCategoryFromList(categoryName: String) {
        var index = 0
        for (genres in favoriteCategoriesList) {
            if (genres.name == categoryName) {
                favoriteCategoriesList.removeAt(index)
                break
            }
            index++
        }
    }

    fun generateCategoryButtons(gen: List<GenresNetworkEntity>) {
        val scrollViewLayout = findViewById<LinearLayout>(R.id.scrollViewLinearLayout)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.setMargins(10, 30, 10, 30)

        for (genre in viewModel.responseFromApi) {
            availableCategories.add(genre)
            val dynamicButton = Button(this)
            dynamicButton.setBackgroundResource(R.drawable.button_selected_case)
            dynamicButton.setOnClickListener {
                specialClickListener(it, genre.id, genre.name)
            }

            dynamicButton.setTextColor(Color.BLUE)
            dynamicButton.text = genre.name

            scrollViewLayout.addView(dynamicButton, layoutParams)
        }
    }

    private fun skipButton() {

        val skipButton: TextView = findViewById<TextView>(R.id.skipButton)
        skipButton.setOnClickListener {
            val nextActivity = Intent(this, LibraryActivity::class.java)
            startActivity(nextActivity)
        }
    }

    private fun nextButton() {

        val nextButton: Button = findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            val nextActivity = Intent(this, LibraryActivity::class.java)
            viewModel.selectedCategoryId?.let { selectedCategoryId ->
                nextActivity.putExtra("category_Id", selectedCategoryId.toString())
            }
            viewModel.selectedCategoryName?.let { selectedCategoryName ->
                nextActivity.putExtra("category_name", selectedCategoryName)
            }
            saveCategories()
            //we saved the categories that were chosen

            startActivity(nextActivity)
        }
    }
}