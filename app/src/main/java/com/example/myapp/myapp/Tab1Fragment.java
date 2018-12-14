package com.example.myapp.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        ListView listView = (ListView)view.findViewById(R.id.tab1_listview);
        listView.setAdapter(new ListViewTab1Adapter(container.getContext(), getTab1List()));
        return view;
    }



    private String [] getTab1List(){

        String[] list = new String [50];
        for (int i = 0; i < 50; i++){
            list[i] = "" + i;
        }
        return list;
    }

}
