package com.example.myapp.myapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewTab1Adapter extends BaseAdapter {
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;

    public ListViewTab1Adapter(Context context, String[] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        boolean status = false;

        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.row, null);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(data[position]);

        Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        ImageView deleteImage = (ImageView) view.findViewById(R.id.delete_image);


        deleteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View parent = (View) v.getParent();
                ImageView img = (ImageView) parent.findViewById(R.id.delete_image);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        AnimatorSet reducer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(), R.animator.reduce_size);
                        reducer.setTarget(img);
                        img.setImageResource(R.drawable.ic_delete_red_24dp);
                        reducer.start();
                        break;


                    case MotionEvent.ACTION_UP:
                        AnimatorSet regainer = (AnimatorSet) AnimatorInflater.loadAnimator(v.getContext(),R.animator.regain_size);
                        regainer.setTarget(img);
                        img.setImageResource(R.drawable.ic_delete_black_24dp);
                        regainer.start();
                        break;
                }
                return true;
            }
        });


        return view;
    }
}