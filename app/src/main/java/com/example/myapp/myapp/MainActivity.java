package com.example.myapp.myapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.app.AlertDialog;

import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    public static SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    public static Database tab1Database;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        loadDatabases();


        setupViewPager();

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

        //TODO create a text prompt
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

                        //TODO put text prompt here https://www.sitepoint.com/starting-android-development-creating-todo-app/
                        final EditText taskEditText = new EditText(v.getContext());
                        AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                                .setTitle("Add a new task")
                                .setMessage("What do you want to do next?")
                                .setView(taskEditText)
                                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String task = String.valueOf(taskEditText.getText());
                                        Log.d(TAG, "Write Database");
                                        tab1Database.writeToDatabase(new Task(tab1Database.getDataHeaders(), task));
                                        Log.d(TAG, "(TabFragments) mSectionsPageAdapter.getCurrentFragment() Start");
                                        TabFragments currentTabFragment = (TabFragments) mSectionsPageAdapter.getCurrentFragment();
                                        Log.d(TAG, "Current Tab Fragment TAG: " + ((Fragment)currentTabFragment).getTag());
                                        Log.d(TAG, "refreshList Start");
                                        currentTabFragment.refreshList();
                                        Log.d(TAG, "refreshList Success");
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog.show();

                        break;
                }
                return true;
            }

        });



    }

    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.container);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mSectionsPageAdapter.addFragment(new Tab1Fragment(), "TAB1");
        mSectionsPageAdapter.addFragment(new Tab2Fragment(), "TAB2");
        mViewPager.setAdapter(mSectionsPageAdapter);
    }

    private void loadDatabases(){
        tab1Database = new Database(getBaseContext(), "now_tasks");
    }
}
