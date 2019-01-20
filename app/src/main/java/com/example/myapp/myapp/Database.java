package com.example.myapp.myapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Database {
    private static final String TAG = "Database";

    private List<Task> data = new ArrayList<Task>();
    private List<String> dataHeaders = new ArrayList<String>();
    private Context context;
    private String filename;
    private File databaseFile;
    String databasePath;

    Database(Context c, String f){
        context = c;
        filename = f;

        databasePath = context.getFilesDir().getAbsolutePath() + "/" + filename + ".csv";
        Log.d("DatabasePath", databasePath);
        databaseFile = new File(databasePath);
        if (!databaseFile.exists()) {
            Log.d(TAG, "Database does not exist");
            try {
                copyRAWtoInternal();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error Copying RAW");
            }
        } else {
            Log.d(TAG, "Database file exists");
        }
        compileDatabase();
    }

    private void copyRAWtoInternal() throws IOException {
        Log.d(TAG, "No Streams created");
        InputStream in = context.getResources().openRawResource(getResId(filename, R.raw.class));
        Log.d(TAG, "Stream 1 created");
        FileOutputStream out = new FileOutputStream(databasePath);
        Log.d(TAG, "Streams created");

        byte[] buff = new byte[1024];
        int read = 0;
        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
                Log.d("Read",  read + "");
            }
        } finally {
            in.close();
            out.close();
        }
        Log.d(TAG, "Database file created");
    }

    private void compileDatabase() {
        try {
            CSVReader reader = new CSVReader(new FileReader(databaseFile.getAbsolutePath()));
            int counter = 0;
            String[] nextLine;

            if (((nextLine = reader.readNext()) != null) && (counter == 0)) {
                setDataHeaders(nextLine);
                counter++;
            }
            while ((nextLine = reader.readNext()) != null){
                data.add(new Task(dataHeaders, nextLine));
                counter++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }
    }

    public void writeToDatabase(Task task) {
        if (!Arrays.equals(task.getHeaders(), dataHeaders.toArray(new String[dataHeaders.size()]))){ //Rewrite file with new headers
            Log.d(TAG, "Starting file rewrite, with new headers");
            String [] taskHeaders = task.getHeaders();
            try {
                Log.d(TAG, "Rewriting headers in csv file");
                CSVWriter writer = new CSVWriter(new FileWriter(databaseFile, false)); // false = Overwrite file
                writer.writeNext(taskHeaders);
                Log.d(TAG, "Headers Written to File");

                Log.d(TAG, "Adding Task to Database ArrayList");
                data.add(task);
                Log.d(TAG, "Added Task to Database ArrayList");

                for (int i = 0; i < data.size(); i++) {
                    writer.writeNext(data.get(i).getFields());
                    Log.d(TAG, "Writing Old Task Fields");
                }
                Log.d(TAG, "Task Fields Written");

                writer.close();

                Log.d(TAG, "File Written");

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error Reading File");
            }

        } else { //Append new task
            Log.d(TAG, "Appending File with new task, headers match");
            String[] fields = task.getFields();

            try {
                Log.d(TAG, "Writing file");
                CSVWriter writer = new CSVWriter(new FileWriter(databaseFile, true));
                Log.d(TAG, "Adding Task to Database ArrayList");
                data.add(task);
                Log.d(TAG, "Added Task to Database ArrayList");

                writer.writeNext(fields);
                writer.close();
                Log.d(TAG, "File Written");

            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error Reading File");
            }

        }
    }
    public Task [] getDatabseTasks() {
        Log.d(TAG, "getDatabaseTasks()");
        return data.toArray(new Task[data.size()]);
    }

    private void setDataHeaders(String [] line) {
        dataHeaders = new ArrayList<String>(Arrays.asList(line));
    }



    public void deleteFromDatabase(int position) {

        data.remove(position);

        Log.d(TAG, "Starting file rewrite");
        String [] taskHeaders = new String[dataHeaders.size()];
        dataHeaders.toArray(taskHeaders);
        try {
            Log.d(TAG, "Rewriting headers in csv file");
            CSVWriter writer = new CSVWriter(new FileWriter(databaseFile, false)); // false = Overwrite file
            writer.writeNext(taskHeaders);
            Log.d(TAG, "Headers Written to File");

            for (int i = 0; i < data.size(); i++) {
                writer.writeNext(data.get(i).getFields());
                Log.d(TAG, "Writing Old Task Fields");
            }
            Log.d(TAG, "Task Fields Written");

            writer.close();

            Log.d(TAG, "File Written");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }

    }




    private static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
