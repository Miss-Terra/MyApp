package com.example.myapp.myapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Vibrator;
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

        view.setTag(position);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "Item On Long Click Action");
                Log.d(TAG, "Item Position: " + (int)v.getTag());

                Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE) ;
                AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(), R.animator.reduce_size);
                AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(),R.animator.regain_size);
                regainer.setTarget(v);
                reducer.setTarget(v);

                reducer.start();
                vibe.vibrate(50);
                regainer.start();


                Task taskToEdit = MainActivity.tab1Database.getTaskByPos((int)v.getTag()); /// Get Task

                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("TASK", (Parcelable)taskToEdit);
                intent.putExtra("DATABASE", 1);

                context.startActivity(intent);

                return true;
            }
        });

        deleteButton.setTag(position);
        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View parent = (View) v.getParent();
                ImageView img = (ImageView) parent.findViewById(R.id.delete_image);
                Log.d(TAG, "Item Position: " + (int)v.getTag());
                Log.d(TAG, "Delete Action Up");
                MainActivity.tab1Database.deleteFromDatabase((int) v.getTag()); /// remove position

                TabFragments currentTabFragment = (TabFragments) MainActivity.mSectionsPageAdapter.getCurrentFragment();
                currentTabFragment.refreshList();
                Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE) ;
                vibe.vibrate(50);



                return true;
            }
        });


        return view;
    }
}