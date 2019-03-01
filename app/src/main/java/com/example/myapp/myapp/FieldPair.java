package com.example.myapp.myapp;

import android.os.Parcel;
import android.os.Parcelable;

public class FieldPair implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    // Write object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(tag);
        out.writeString(data);

    }

    /*
    <<<<<<<<<<< Parcelable Code >>>>>>>>>>>>
     */

    // Regenerate object.
    public static final Parcelable.Creator<FieldPair> CREATOR = new Parcelable.Creator<FieldPair>() {
        public FieldPair createFromParcel(Parcel in) {
            return new FieldPair(in);
        }

        public FieldPair[] newArray(int size) {
            return new FieldPair[size];
        }
    };
    // Constructor that takes in a Parcel to generate an object with values
    private FieldPair(Parcel in) {
        tag = in.readString();
        data = in.readString();
    }
}
