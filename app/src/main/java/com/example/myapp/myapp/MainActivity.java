package com.example.myapp.myapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public static int tabPosition = 0;

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    public static Database tab1Database;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        loadDatabases();

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the seconds adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "tabSelected: " + tab.getPosition());
                //https://stackoverflow.com/questions/28003788/fragmentpageradapter-how-to-detect-a-swipe-or-a-tab-click-when-user-goes-to-a
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d(TAG, "tabUnselected: " + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d(TAG, "tabReselected: " + tab.getPosition());
            }
        });

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


        floatingActionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(), R.animator.reduce_size);
                        reducer.setTarget(v);
                        reducer.start();
                        break;


                    case MotionEvent.ACTION_UP:
                        AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(),R.animator.regain_size);
                        regainer.setTarget(v);
                        regainer.start();
                        break;
                }
                return true;
            }

        });



    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "TAB1");
        adapter.addFragment(new Tab2Fragment(), "TAB2");
        viewPager.setAdapter(adapter);
    }

    private void loadDatabases(){
        tab1Database = new Database(getBaseContext(), "now_tasks.csv");
    }
}
