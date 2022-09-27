package com.example.wirtalks.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wirtalks.R
import com.example.wirtalks.model.GenresNetworkEntity
import com.example.wirtalks.model.PodcastNetworkEntity
import com.example.wirtalks.viewmodel.CategorySelectionActivityViewModel
import com.example.wirtalks.viewmodel.LibraryActivityViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import com.squareup.picasso.Picasso
import kotlin.random.Random


class LibraryFragment : Fragment() {

    lateinit var viewAllFromPlayList: TextView
    lateinit var viewAllFromTopPodcast: TextView

    var categoryId: String = ""
    var categoryName: String = ""

    lateinit var viewModel: LibraryActivityViewModel
    lateinit var horizontalLinearLayout: LinearLayout

    lateinit var sharedPreferences : SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("lib frag", "onviewcreated called")

        if (!newUser) {
            // case when categories are selected (pressed NEXT button)
            val selectedCategories = getListOfSelectedCategories()
            //get random category
            val randomCategory = selectedCategories.random(Random(System.currentTimeMillis()))

            categoryId = randomCategory.id.toString()
            categoryName = randomCategory.name

            fetchTopPodcastImage()
        } else {
            // case when categories aren't selected (pressed SKIP button)
            val randomCategory = availableCategories.random(Random(System.currentTimeMillis()))

            categoryId = randomCategory.id.toString()
            categoryName = randomCategory.name
            fetchRandomPodcastImage()
        }
        val topPodcast: TextView = view.findViewById(R.id.top_podcast_text)
        topPodcast.text = "Top podcast in ${categoryName}"

        viewAllFromPlaylist()
        viewAllFromTopPodcast()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {

        }
        Log.e("lib frag", "onCreate called")

        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    //Get data from shared preferences
    private fun getListOfSelectedCategories(): ArrayList<GenresNetworkEntity> {
        val sharedPreferences = requireContext().getSharedPreferences("saveData", Context.MODE_PRIVATE)
        val categoriesJson = sharedPreferences?.getString("key favoriteCategories", "noCategory")
        val gson = Gson()
        val typeToken = object : TypeToken<ArrayList<GenresNetworkEntity>>() {}.type

        return gson.fromJson(categoriesJson, typeToken)
    }


    private fun viewAllFromPlaylist() {
        viewAllFromPlayList = view?.findViewById(R.id.view_all_from_playlist)!!
        viewAllFromPlayList.setOnClickListener {
            val viewAllPlayList = Intent(context, PlaylistActivity::class.java)
            startActivity(viewAllPlayList)

        }
    }

    private fun viewAllFromTopPodcast() {
        viewAllFromTopPodcast = view?.findViewById(R.id.viewAllinTopPodcasts)!!
        viewAllFromTopPodcast.setOnClickListener {
            val viewAllFromTopPodcastIntent = Intent(context, TopPodcastsViewAllActivity::class.java)
            viewAllFromTopPodcastIntent.putExtra("category_name", categoryName)
            startActivity(viewAllFromTopPodcastIntent)
        }
    }

    private fun fetchRandomPodcastImage() {
        horizontalLinearLayout = view?.findViewById(R.id.horizontalScrollViewLinearLayout)!!
        val horizontalLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        horizontalLayoutParams.setMargins(0, 0, 20, 0)

        viewModel = LibraryActivityViewModel()
        viewModel.fetchPodcasts(
            categoryId ?: "77",
            object : LibraryActivityViewModel.PodcastListener {
                override fun onComplete(podcast: List<PodcastNetworkEntity>) {
                    var count = 1
                    for (res in podcast) {
                        //Adding the MediaStore.Images from the URL Requests (max 5)
                        if (count <= 5) {
                            val dynamicImage = ImageView(context)
                            Picasso.with(context).load(res.image)
                                .resize(400, 400)
                                .into(dynamicImage)
                            dynamicImage.setOnClickListener {
                                dynamicImageClickListener(res.id, res.title, res.image, "pub")
                            }
                            horizontalLinearLayout.addView(dynamicImage, horizontalLayoutParams)
                            count++
                        }
                    }
                }
            }
        )
    }

    private fun fetchTopPodcastImage() {
        horizontalLinearLayout = view?.findViewById(R.id.horizontalScrollViewLinearLayout)!!
        val horizontalLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        horizontalLayoutParams.setMargins(0, 0, 20, 0)
        
        viewModel = LibraryActivityViewModel()
        viewModel.fetchPodcasts(
            categoryId ?: "77",
            object : LibraryActivityViewModel.PodcastListener {
                override fun onComplete(podcast: List<PodcastNetworkEntity>) {
                    var count = 1
                    for (res in podcast) {
                        //Adding the MediaStore.Images from the URL Requests (max 5)
                        if (count <= 5) {
                            val dynamicImage = ImageView(context)
                            Picasso.with(context).load(res.image)
                                .resize(400, 400)
                                .into(dynamicImage)
                            dynamicImage.setOnClickListener {
                                dynamicImageClickListener(res.id, res.title, res.image, "pub")
                            }
                            horizontalLinearLayout.addView(dynamicImage, horizontalLayoutParams)
                            count++
                        }
                    }
                }
            }
        )
    }

    private fun dynamicImageClickListener(podcastId: String, podcastTitle: String, podcastImage: String, podcastPublisher: String) {
        // Send data to PodcastChannelActivity
        val podcastChannelActivity = Intent(context, PodcastChannelActivity::class.java)
        podcastChannelActivity.putExtra("podcastId",podcastId)
        podcastChannelActivity.putExtra("podcastTitle",podcastTitle)
        podcastChannelActivity.putExtra("podcastImage",podcastImage)
        podcastChannelActivity.putExtra("podcastPublisher", podcastPublisher)
        startActivity(podcastChannelActivity)
    }

}