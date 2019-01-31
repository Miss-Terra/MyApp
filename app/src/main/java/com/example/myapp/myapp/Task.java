package com.example.myapp.myapp;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private static final String TAG = "Task";
    private List<FieldPair> fields = new ArrayList<FieldPair>();
    private String headers[];


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

    Task(List<String> headers, String name){
        this.headers = headers.toArray(new String[headers.size()]);

        for (int i = 0; i < this.headers.length; i++) {
            if (i != 1) { // 1 = name
                fields.add(new FieldPair(this.headers[i], ""));
            } else {
                fields.add(new FieldPair(this.headers[i], name));
            }
        }
    }

    Task(){
        this.headers = new String[] {"Position", "Name", "Description", "Field2", "Field3"};
        for (int i = 0; i < this.headers.length; i++) {
                fields.add(new FieldPair(this.headers[i], "dummy" + i));
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
    public String getField(int headerColumn) {

        Log.d(TAG, "Getting data for " + headers[headerColumn] + " (" + headerColumn + "): ");
        Log.d(TAG, fields.get(headerColumn).getData());
        return fields.get(headerColumn).getData();

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

    public static String [] getTaskDataListByHeader (int headerColumn, Task[] tasklist) {
        String[] result = new String[tasklist.length];
        for (int i = 0; i < tasklist.length; i++) {
            Log.d(TAG, tasklist[0].getField(0));

            result[i] = tasklist[i].getField(headerColumn);
        }
        return result;
    }
}
