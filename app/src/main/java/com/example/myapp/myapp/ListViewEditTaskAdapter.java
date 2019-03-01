package com.example.myapp.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ListViewEditTaskAdapter extends BaseAdapter {
    private static final String TAG = "ListViewEditTaskAdapter";
    Context context;
    ArrayList<String> headers = new ArrayList<String>();
    ArrayList<String> fields = new ArrayList<String>();
    Task task; //TODO this is a bit messy.
    Database db;

    private static LayoutInflater inflater = null;


    /*public ListViewEditTaskAdapter(Context context, ArrayList<String> headers, ArrayList<String> fields) {
        this.context = context;
        this.headers = headers;
        this.fields = fields;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }*/
    public ListViewEditTaskAdapter(Context context, String[] headers, String[] fields, Task task, Database db) {
        this.context = context;
        this.headers.clear();
        this.fields.clear();
        this.task = task;
        this.db = db;

        Log.d(TAG, "headers size = " + headers.length);
        Log.d(TAG, "fields size = " + fields.length);

        Collections.addAll(this.headers, headers);
        Collections.addAll(this.fields, fields);

        Log.d(TAG, "this.headers size = " + this.headers.size());
        Log.d(TAG, "this.fields size = " + this.fields.size());

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);




    }

   /* public void updateList(ArrayList<String> newList) {
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
*/
    @Override
    public int getCount() {
        return headers.size();
    }

    @Override
    public Object getItem(int position) {
        return fields.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }




    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView header;
        TextView field;

        View view = convertView;
        if (view == null) {
            if (position == 0 || position == 1) {
                view = inflater.inflate(R.layout.blank_layout, null);
            } else {
                view = inflater.inflate(R.layout.task_field, null);
            }

            header = (TextView) view.findViewById(R.id.header);
            field = (TextView) view.findViewById(R.id.field);
            field.setText(fields.get(position));
        } else {
            header = (TextView) view.findViewById(R.id.header);
            field = (TextView) view.findViewById(R.id.field);
        }

        if (position == 0) {
            view.setVisibility(View.GONE);
        } else {

            header.setText(headers.get(position));

            field.setOnLongClickListener(new OnLongClickListenerTaskField(view.getContext(), task, position, field, db));


            view.setTag(position);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d(TAG, "Item On Long Click Action");
                    Log.d(TAG, "Item Position: " + (int) v.getTag());


                    //Task taskToEdit = MainActivity.tab1Database.getTaskByPos((int)v.getTag()); /// Get Task


                    return true;
                }
            });
        }


        return view;
    }



}