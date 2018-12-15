package com.example.myapp.myapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Database {

    private String filename;
    private List<String> data = new ArrayList<String>();
    private Context context;

    //TODO check if context passes here correctly...
    //TODO https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app
    Database(Context c, String f){
        context = c;
        //TODOdatadir is broken
        filename = context.getApplicationInfo().dataDir + File.separatorChar + f;
        Log.d("VariableTag",filename);
        compileDatabase();
    }

    private void compileDatabase() {
        try {
            //TODO this might be broke
            File csvfile = new File(filename);
            //CSVReader reader = new CSVReader(new InputStreamReader(context.getResources().openRawResource(R.raw.now_tasks)));//Specify asset file name

            //TODO this might be broke
            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));

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
    public void writeDatabase(String s) {
        String[] newItem = {s};
        File csvfile = new File(filename);
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvfile, true));

            writer.writeNext(newItem);
            writer.close();
            data.add(newItem[0]);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }
    }

    public ArrayList<String> readDatabse() {
        return (ArrayList<String>) data;
    }
}
