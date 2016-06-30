package com.jikexueyuan.listviewexample.controllers;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jikexueyuan.listviewexample.R;
//4 使用ListActivity
public class ListActivityExampleActivity extends ListActivity {
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity_example);//ListActivity的自定义布局资源（自定义后会
        // 替换掉下述ArrayAdapter自己的布局资源,故xml中不可改：android:id="@android:id/list"）
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);//在ListActivity
        // 的自定义布局资源的基础上，使用系统ArrayAdapter的单条item的布局资源。
        adapter.add("姐姐");
        adapter.add("妹妹");
        setListAdapter(adapter);
    }
}
