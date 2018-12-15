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


    private List<String> data = new ArrayList<String>();
    private Context context;
    private String filename;
    private File databaseFile;
    String databasePath;
    //TODO check if context passes here correctly...
    //TODO https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app
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


        //TODO datadir is broken
        //filename = context.getApplicationInfo().dataDir + File.separatorChar + f;
       //Log.d("VariableTag",filename);




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

            //TODO this might be broke
            //File csvfile = new File(filename);
            //CSVReader reader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.now_tasks)));//Specify asset file name

            //TODO this might be broke
            CSVReader reader = new CSVReader(new FileReader(databaseFile.getAbsolutePath()));

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null){
                //Log.d("VariableTag", nextLine[0]);
                data.add(nextLine[0]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }
    }

    //TODO This is broken.
    public void writeToDatabase(String s) {
        String[] newItem = {s};
       // File csvfile = new File(filename);
        try {
            Log.d(TAG, "Writing file");
            CSVWriter writer = new CSVWriter(new FileWriter(databaseFile, true));

            writer.writeNext(newItem);
            writer.close();
            data.add(newItem[0]);
            Log.d(TAG, "File Written");


        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }
    }

    public ArrayList<String> readDatabse() {
        return (ArrayList<String>) data;
    }










    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
