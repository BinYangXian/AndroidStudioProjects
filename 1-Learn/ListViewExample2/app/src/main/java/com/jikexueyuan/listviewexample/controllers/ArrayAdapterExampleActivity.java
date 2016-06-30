package com.jikexueyuan.listviewexample.controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jikexueyuan.listviewexample.R;
import com.jikexueyuan.listviewexample.models.Student;
//2 自定义列表项数据模型\3 与列表交互
public class ArrayAdapterExampleActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<Student> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1);//参数：Context、"android."表示android系统，缺省时为当前
        // 系统提供的模板布局资源。
        adapter.add(new Student("侯佩岑", 32));
        adapter.add(new Student("徐静蕾",33));
        adapter.add(new Student("周杰伦", 34));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Student student = adapter.getItem(position);
                new AlertDialog.Builder(ArrayAdapterExampleActivity.this)
                        .setTitle("详细信息")
                        .setMessage("名字：" + student.getName() + ",年龄:" + student.getAge())
                        .setPositiveButton("关闭", null)
                        .show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ArrayAdapterExampleActivity.this)
                        .setTitle("点击“删除”按钮删除此项")
                        .setNegativeButton("取消", null).setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(adapter.getItem(position));
                    }
                })
                        .show();
                return false;
            }
        });
        listView.setAdapter(adapter);
    }
}
