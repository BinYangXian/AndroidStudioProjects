package com.jikexueyuan.listviewexample.controllers;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.jikexueyuan.listviewexample.models.CheckBoxItem;

//5 使用CheckBoxListView
public class CheckBoxesExampleActivity extends ListActivity {
private ArrayAdapter<CheckBoxItem> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ArrayAdapter<CheckBoxItem>(this,android.R.layout.simple_list_item_multiple_choice){
            //重写覆盖ArrayAdapter类中的getView函数
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                CheckedTextView ctv= (CheckedTextView) super.getView(position, convertView, parent);
                //我们只需要在这里配置ctv的状态，让它的数据与CheckBoxItem所配置的数据的状态保持一致就ok；
                //那我们为什么要重写getView这个函数呢?因为它会在，一旦有新的列表项需要显示的时候就执行相应次数，
                // （此处是系统封装好的）通过getView的返回值（这里是ctv），把它添加到listView的后边：只需要下两步，完成数据的同步！
                CheckBoxItem item=getItem(position);   //在adapter的内部可以直接访问到此getItem函数
                ctv.setChecked(item.isChecked());//列表项的状态与数据（item）的状态保持同步,此处的setChecked方法是系统设置
                                                 // 列表项状态的方法，而非自定义数据类型CheckBoxItem中的setChecked方法。
                return ctv;
            }
        };
        setListAdapter(adapter);
//        adapter.add("hello");//此处当显示条目多了时候会出现问题，这里不是为了教授此复选框功能，而是原理
//        adapter.add("world");
        //添加时间item监听器，可以用getListView().setOnItemClickListener...；也可以下述方法
        for (int i = 0; i < 100; i++) {
            adapter.add(new CheckBoxItem("Item "+i,false));
        }
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //通过 System.out.println(v);可以查看到v实际为CheckedTextView类型,此处当显示条目多了
        // 时候会出现问题，这里不是为了教授此复选框功能，而是为观察其原理。
//        CheckedTextView ctv= (CheckedTextView) v;//当选中的时候就不要直接操作CheckedTextView的对象了,而是操作数据
//        ctv.setChecked(!ctv.isChecked());        //当选中的时候就不要直接操作CheckedTextView的对象了,而是操作数据
        CheckBoxItem item=adapter.getItem(position);//而是操作数据
        item.setChecked(!item.isChecked());          //而是操作数据
        adapter.notifyDataSetChanged();             //数据操作完成后，不会立即显示，所以用此方法
    }
}
