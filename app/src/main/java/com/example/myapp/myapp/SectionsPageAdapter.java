package com.example.myapp.myapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public class SectionsPageAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionsPageAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Fragment mCurrentFragment;

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }




    public Fragment getCurrentFragment() {
        Log.d(TAG, "Returning Current Fragment");
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "Set Primary Fragment");
        if (getCurrentFragment() != object) {
            Log.d(TAG, "Current Fragment = null");
            mCurrentFragment = ((Fragment) object);
            Log.d(TAG, "Set Primary Fragment Defined. TAG: " + mCurrentFragment.getTag());
        }
        super.setPrimaryItem(container, position, object);
    }
}
