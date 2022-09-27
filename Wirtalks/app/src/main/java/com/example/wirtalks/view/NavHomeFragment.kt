package com.example.wirtalks.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.wirtalks.R
import com.example.wirtalks.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NavHomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("nav_home frag", "onviewcreated called")

        val viewpager2 = view.findViewById<ViewPager2>(R.id.viewpager_2)
        viewpager2.adapter = ViewPagerAdapter(activity) //Attach the adapter with our ViewPagerAdapter passing the host activity


        val tabLayout: TabLayout = view.findViewById(R.id.all_tabs)
        TabLayoutMediator(
            tabLayout, viewpager2
        ) { tab, position ->
            tab.text = (viewpager2.getAdapter() as ViewPagerAdapter).mFragmentNames[position] //Sets tabs names as mentioned in ViewPagerAdapter fragmentNames array, this can be implemented in many different ways.
        }.attach()
    }




}