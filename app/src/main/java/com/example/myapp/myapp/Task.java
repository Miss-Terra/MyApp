package com.example.myapp.myapp;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private static final String TAG = "Task";
    private List<FieldPair> fields = new ArrayList<FieldPair>();
    private String headers[];

    //TODO working on compileDatabase() and changing the format of the raw csv file.
    Task(List<String> headers, String[] data) {
        this.headers = headers.toArray(new String[headers.size()]);
        if (this.headers.length == data.length) {
            for (int i = 0; i < this.headers.length; i++) {
                fields.add(new FieldPair(this.headers[i], data[i]));
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

    public String [] getFields(){
        int size = fields.size();
        String [] s = new String[size];
        for (int i = 0; i < size; i++){
            s[i] = fields.get(i).getData();
        }
        return s;
    }
    public String [] getHeaders() {
        return headers;
    }

    public static String [] getTaskDataListByHeader (String header, Task[] tasklist) {
        String [] headers = tasklist[0].getHeaders();
        int i = 0;
        while (!headers[i].equals(header)){
            i++;
            if (i >= headers.length) {
                return null;
            }
        }
        return getTaskDataListByHeader(i, tasklist);

    }
    //TODO fix me
    public static String [] getTaskDataListByHeader (int headerColumn, Task[] tasklist) {
        int i = 0;
        while (tag.equals(task[i].fields.))
    }
}
