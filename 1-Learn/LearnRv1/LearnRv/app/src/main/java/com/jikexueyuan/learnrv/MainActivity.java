package com.jikexueyuan.learnrv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rv=new RecyclerView(this);
        setContentView(rv);//设置内容布局为rv

//        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));//设置rv的内容布局,参数3.是否反转。
        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setLayoutManager(new GridLayoutManager(this,4,LinearLayoutManager.HORIZONTAL,false));//表格布局，参数2.列数。3.水平或垂直，4.是否反转。
//        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(new MyAdapter());
    }

}
