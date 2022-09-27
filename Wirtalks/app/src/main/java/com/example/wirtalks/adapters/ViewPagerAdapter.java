package com.example.wirtalks.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.wirtalks.view.LibraryFragment;
import com.example.wirtalks.view.DiscoverFragment;
import com.example.wirtalks.view.FavoriteFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    private final Fragment[] mFragments = new Fragment[]{//Initialize fragments views
            //Fragment views are initialized like any other fragment (Extending Fragment)
            new LibraryFragment(),//First fragment to be displayed within the pager tab number 1
            new DiscoverFragment(),
            new FavoriteFragment()
    };
    public final String[] mFragmentNames = new String[]{//Tabs names array
            "Listen now",
            "Discover",
            "Favorite"
    };

    public ViewPagerAdapter(FragmentActivity fa) {//Pager constructor receives Activity instance
        super(fa);
    }

    @Override
    public int getItemCount() {
        return mFragments.length;//Number of fragments displayed
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments[position];
    }
}
