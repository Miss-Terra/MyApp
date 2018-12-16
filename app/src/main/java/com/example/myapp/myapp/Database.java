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

    public void writeToDatabase(String s) {
        String[] newItem = {s};
        try {
            Log.d(TAG, "Writing file");
            CSVWriter writer = new CSVWriter(new FileWriter(databaseFile, true));

            writer.writeNext(newItem);
            writer.close();
            //TODO fix this, data now takes 'TASKS'
            data.add(newItem[0]);
            Log.d(TAG, "File Written");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }
    }
    //TODO fix this, data now takes 'TASKS'
    public ArrayList<String> readDatabse() {
        Log.d(TAG, "readDatabase()");
        return (ArrayList<String>) data;
    }

    private void setDataHeaders(String [] line) {
        dataHeaders = new ArrayList<String>(Arrays.asList(line));
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
