package com.example.myapp.myapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task implements Parcelable {
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

    Task(List<String> headers, String name, int position){
        this.headers = headers.toArray(new String[headers.size()]);

        for (int i = 0; i < this.headers.length; i++) {
            if (i != 1) { // 1 = name
                if (i == 0) {
                    fields.add(new FieldPair(this.headers[i], position + ""));
                } else {
                    fields.add(new FieldPair(this.headers[i], ""));
                }
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

    public void setField(int headerColumn, String data) {

        Log.d(TAG, "Setting data for " + headers[headerColumn] + " (" + headerColumn + "): ");
        fields.get(headerColumn).setData(data);
        Log.d(TAG, "Header " + headers[headerColumn] + " updated to: " + data);

    }

    public String [] getHeaders() {
        return headers;
    }

    public static String [] getTaskDataListByHeader (String header, Task[] tasklist) {
        Log.d(TAG, "Tasklist array size: " + tasklist.length);
        //If no tasks are found... Return a empty string array. Collections.AddAll in ListViewTab1Adapter.class will treat it as empty.
        if (tasklist.length == 0) {
            Log.d(TAG, "No tasks, returning none");
            return new String[0];
        }
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

    /*
    <<<<<<<<<<< Parcelable Code >>>>>>>>>>>>
     */

    @Override
    public int describeContents() {

        return 0;
    }

    // Write object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        Log.d(TAG, "Converting Task to Parcel");
        out.writeStringArray(headers);
        out.writeTypedList(fields); //TODO ERROR Fields doesn't like to get passed as a parcable
        Log.d(TAG, "Converted Task to Parcel");
    }

    // Regenerate object.
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {

            return new Task(in);
        }

        public Task[] newArray(int size) {

            return new Task[size];
        }
    };

    //TODO ERROR Fields doesn't like to get passed as a parcable
    // Constructor that takes in a Parcel to generate an object with values
    private Task(Parcel in) {
        Log.d(TAG, "Constructing Task from Parcel");
        headers = in.createStringArray();
        in.readTypedList(fields, FieldPair.CREATOR);
        Log.d(TAG, "Constructed Task from Parcel");
    }

}
