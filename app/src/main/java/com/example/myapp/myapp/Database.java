package com.example.myapp.myapp;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Database {

    private String filename;
    private List<String> data = new ArrayList<String>();
    private Context context;

    //TODO check if context passes here correctly...
    //https://stackoverflow.com/questions/43055661/reading-csv-file-in-android-app
    public void database(Context c, String f){
        filename = context.getApplicationInfo().dataDir + File.separatorChar + f;
    }

    public void compileDatabase() {
        File csvfile = new File(filename);
        try {
            CSVReader reader = new CSVReader(new FileReader("csvfile.getAbsolutePath()"));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null);
            //TODO make sure arraylist works
            data = Arrays.asList(nextLine);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }
    }

    //TODO Finish this
    public void writeDatabase(String newItem) {
        File csvfile = new File(filename);
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csvfile));
            //TODO Put 'try' code above add new item. (Order maters)
            data.add(newItem);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error Reading File");
        }

    }
}
