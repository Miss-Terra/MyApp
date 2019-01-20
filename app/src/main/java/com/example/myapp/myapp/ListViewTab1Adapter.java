package com.example.myapp.myapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ListViewTab1Adapter extends BaseAdapter {
    private static final String TAG = "ListViewTab1Adapter";
    Context context;
    ArrayList<String> data = new ArrayList<String>();
    private static LayoutInflater inflater = null;


    public ListViewTab1Adapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public ListViewTab1Adapter(Context context, String[] data) {
        this.context = context;
        this.data.clear();
        Log.d(TAG, "data size = " + data.length);
        Collections.addAll(this.data, data);
        Log.d(TAG, "this.data size = " + this.data.size());
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateList(ArrayList<String> newList) {
        Log.d(TAG, "updateList()");
        data = newList;
        Log.d(TAG, "data = newList");
        notifyDataSetChanged();
        Log.d(TAG, "notifyDataSetChanged()");
    }

    public void updateList(Task[] tasks) {
        Log.d(TAG, "updateList()");
        ArrayList<String> newList;
        data.clear();
        Collections.addAll(data, Task.getTaskDataListByHeader("Name", tasks));
        Log.d(TAG, "data = newList");
        notifyDataSetChanged();
        Log.d(TAG, "notifyDataSetChanged()");
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.row, null);
        }
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(data.get(position));

        Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        ImageView deleteImage = (ImageView) view.findViewById(R.id.delete_image);


        //TODO set position here for reference. See https://stackoverflow.com/questions/20541821/get-listview-item-position-on-button-click
        deleteButton.setTag(position);
        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View parent = (View) v.getParent();
                ImageView img = (ImageView) parent.findViewById(R.id.delete_image);
                Log.d(TAG, "Item Position: " + (int)v.getTag());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "Action Down");
                        AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(), R.animator.reduce_size);
                        reducer.setTarget(img);
                        img.setImageResource(R.drawable.ic_delete_red_24dp);
                        reducer.start();
                        break;

                    //TODO Implement Deletion of Task here. Need to figure out how to select correct listview position & task
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "Action Up");
                        AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(),R.animator.regain_size);
                        regainer.setTarget(img);
                        img.setImageResource(R.drawable.ic_delete_black_24dp);
                        regainer.start();
                        MainActivity.tab1Database.deleteFromDatabase((int)v.getTag()); /// remove position

                        TabFragments currentTabFragment = (TabFragments) MainActivity.mSectionsPageAdapter.getCurrentFragment();
                        currentTabFragment.refreshList();

                        break;
                }
                return true;
            }
        });


        return view;
    }
}