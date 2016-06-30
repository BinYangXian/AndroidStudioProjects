package com.jikexueyuan.listviewexample.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jikexueyuan.listviewexample.R;
import com.jikexueyuan.listviewexample.models.Student;

public class CustomListActivity extends AppCompatActivity {
    private ListView listView;
    private CustomListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list);

        listView= (ListView) findViewById(R.id.listView);
        adapter=new CustomListAdapter(this);

        adapter.add(new Student("xiaoli",16));
        adapter.add(new Student("xiaohong",17));
        adapter.add(new Student("yingying",18));
        listView.setAdapter(adapter);
    }
}
