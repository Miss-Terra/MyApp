package com.example.myapp.myapp;

public class FieldPair {
    private static final String TAG = "FieldPair";
    private String tag;
    private String data;

    FieldPair(String tag, String data) {
        this.tag = tag;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public String getData() {
        return data;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setData(String data) {
        this.data = data;
    }



}
