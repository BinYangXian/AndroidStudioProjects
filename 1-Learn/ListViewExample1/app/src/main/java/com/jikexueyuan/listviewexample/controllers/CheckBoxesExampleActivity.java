package com.jikexueyuan.listviewexample.controllers;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.jikexueyuan.listviewexample.models.CheckBoxItem;

public class CheckBoxesExampleActivity extends ListActivity {
private ArrayAdapter<CheckBoxItem> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice);
        setListAdapter(adapter);
//        adapter.add("hello");//此处当显示条目多了时候会出现问题，这里不是为了教授此复选框功能，而是原理
//        adapter.add("world");
        //添加时间item监听器，可以用getListView().setOnItemClickListener...；也可以下述方法

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //通过 System.out.println(v);可以查看到v实际为CheckedTextView类型,此处当显示条目多了
        // 时候会出现问题，这里不是为了教授此复选框功能，而是原理
        CheckedTextView ctv= (CheckedTextView) v;
        ctv.setChecked(!ctv.isChecked());
    }
}
