package com.example.myapp.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import static android.support.constraint.Constraints.TAG;

public class OnLongClickListenerTaskField implements View.OnLongClickListener {

    Context context;
    Task task;
    int headerColumn;
    TextView taskField;
    Database database;

    OnLongClickListenerTaskField(Context context, Task task, int headerColumn, TextView taskField, Database database){
        this.context = context;
        this.task = task;
        this.headerColumn = headerColumn;
        this.taskField = taskField;
        this.database = database;
    }

    @Override
    public boolean onLongClick(View v) {
        Log.d(TAG, "Task Name Long Click");

        Vibrator vibe = (Vibrator)  context.getSystemService(Context.VIBRATOR_SERVICE) ;
        vibe.vibrate(50);


        final EditText editText = new EditText(v.getContext());
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editText.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager inputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "Pressed Save");
                String newText = String.valueOf(editText.getText());
                Log.d(TAG, "Create newText String variable");
                task.setField(headerColumn, newText);
                Log.d(TAG, "Update task variable's field");
                taskField.setText(newText); //TODO Do I really need to remove this...... As long as the DB and text fields both change....
                Log.d(TAG, "updating task in DB position " + Integer.parseInt(task.getField(0)));
                database.updateDatabase(task, Integer.parseInt(task.getField(0))); // 0 == position
/*
                        Log.d(TAG, "Write Database");
                        tab1Database.writeToDatabase(new Task(tab1Database.getDataHeaders(), task));
                        Log.d(TAG, "(TabFragments) mSectionsPageAdapter.getCurrentFragment() Start");
                        TabFragments currentTabFragment = (TabFragments) mSectionsPageAdapter.getCurrentFragment();
                        Log.d(TAG, "Current Tab Fragment TAG: " + ((Fragment)currentTabFragment).getTag());
                        Log.d(TAG, "refreshList Start");
                        currentTabFragment.refreshList();
                        Log.d(TAG, "refreshList Success");
                       */
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("Edit");
        builder.setMessage("What do you want to do next?");
        builder.setView(editText);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
        Log.d(TAG, "Text Size: " + dialog.getButton(DialogInterface.BUTTON_POSITIVE).getTextSize());
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextSize(25.0f);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(25.0f);
        Log.d(TAG, "Text Size: " + dialog.getButton(DialogInterface.BUTTON_POSITIVE).getTextSize());

        dialog.create();

        editText.requestFocus();


        return true;
    }
}
