package com.example.myapp.myapp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class EditTaskActivity extends AppCompatActivity {
    private static final String TAG = "EditTaskActivity";
    private Task task;
    private ListView listView;
    private TextView taskName;

    private ListViewEditTaskAdapter listViewEditTaskAdapter;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Log.d(TAG, "Creating New Activity: " + TAG);

        Intent i = getIntent();
        task = (Task) i.getParcelableExtra("TASK");
        int dbTab = i.getIntExtra("DATABASE", 0);
        if (dbTab == 1){
            database = MainActivity.tab1Database;
        } else {
            database = null;
        }

        Log.d(TAG, "Task " + task.getField(1) + " retrieved from parcel");


        taskName = (TextView) findViewById(R.id.task_name);
        taskName.setText(task.getField(1)); //Header Column 1 = Task Name




        taskName.setOnLongClickListener(new OnLongClickListenerTaskField(this, task, 1, taskName, database)); //TODO pass database as intent instead




        listView = (ListView)findViewById(R.id.task_fields_listview);


        listViewEditTaskAdapter = new ListViewEditTaskAdapter(this, task.getHeaders(), task.getFields(), task, database);
        listView.setAdapter(listViewEditTaskAdapter);


    }


}
