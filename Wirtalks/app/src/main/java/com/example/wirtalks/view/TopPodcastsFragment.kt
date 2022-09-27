package com.example.wirtalks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.wirtalks.R
import com.example.wirtalks.viewmodel.LibraryActivityViewModel


class TopPodcastsFragment : Fragment() {

    lateinit var viewModel: LibraryActivityViewModel
    lateinit var horizontalLinearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_top_podcasts, container, false)
    }


}