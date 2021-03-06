package com.example.myapp.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Tab1Fragment extends Fragment implements TabFragments {
    private static final String TAG = "Tab1Fragment";
    private ListView listView;
    private ListViewTab1Adapter listViewTab1Adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        listView = (ListView)view.findViewById(R.id.tab1_listview);
        Task [] tasks = MainActivity.tab1Database.getDatabseTasks();

        Log.d(TAG, tasks[0].getField(0));

        listViewTab1Adapter = new ListViewTab1Adapter(container.getContext(), Task.getTaskDataListByHeader(1, tasks));
        listView.setAdapter(listViewTab1Adapter);

        return view;
    }



  /*  private String [] getTab1List(){

        String[] list = new String [50];
        for (int i = 0; i < 50; i++){
            list[i] = "" + i;
        }
        return list;
    }
*/
    public void refreshList() {
        Log.d(TAG, "refreshList()");
        listViewTab1Adapter.updateList(MainActivity.tab1Database.getDatabseTasks());
        Log.d(TAG, "refreshList() success");
    }

}
