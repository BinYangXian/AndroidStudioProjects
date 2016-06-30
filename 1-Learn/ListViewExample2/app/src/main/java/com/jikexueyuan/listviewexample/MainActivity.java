package com.jikexueyuan.listviewexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jikexueyuan.listviewexample.controllers.ArrayAdapterExampleActivity;
import com.jikexueyuan.listviewexample.controllers.CheckBoxesExampleActivity;
import com.jikexueyuan.listviewexample.controllers.CustomListActivity;
import com.jikexueyuan.listviewexample.controllers.ListActivityExampleActivity;
import com.jikexueyuan.listviewexample.models.ExampleItem;

/**
 * Created by fangc on 2016/2/21.
 */
public class MainActivity extends Activity {
    private ListView listView;
    private ArrayAdapter<ExampleItem> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listView);
        adapter=new ArrayAdapter<ExampleItem>(this,android.R.layout.simple_list_item_1);
        //config the list items data
        adapter.add(new ExampleItem("ArrayAdapter示例") {//2 自定义列表项数据模型\3 与列表交互
            @Override
            public void onAction() {  //方法重写，实现与setOnItemClickListener列表交互时实现多态性的妙法！！！
                startActivity(new Intent(MainActivity.this, ArrayAdapterExampleActivity.class));
            }
        });
        adapter.add(new ExampleItem("ListActivity示例") {//4 使用ListActivity
            @Override
            public void onAction() {
                startActivity(new Intent(MainActivity.this, ListActivityExampleActivity.class));
            }
        });
        adapter.add(new ExampleItem("CheckBoxesListView示例") {//5 使用CheckBoxListView
            @Override
            public void onAction() {
                startActivity(new Intent(MainActivity.this, CheckBoxesExampleActivity.class));
            }
        });
        adapter.add(new ExampleItem("自定义列表示例") {
            @Override
            public void onAction() {
                startActivity(new Intent(MainActivity.this, CustomListActivity.class));
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position).onAction();
            }
        });
    }
}