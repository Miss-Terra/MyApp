package com.example.myapp.myapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private static final String TAG = "Task";
    private List<FieldPair> fields = new ArrayList<FieldPair>();

    //TODO working on compileDatabase() and changing the format of the raw csv file.
    Task(List<String> headers, String[] data) {
        if (headers.size() == data.length) {
            for (int i = 0; i < headers.size(); i++) {
                fields.add(new FieldPair(headers.get(i), data[i]));
            }
        } else {
            Log.d(TAG, "Header and Data sizes vary");
        }
    }

    // Display all data fields as one string for testing.
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < fields.size(); i++) {

            s = s + fields.get(i).getData() + ',';

        }
        return s;
    }
}
