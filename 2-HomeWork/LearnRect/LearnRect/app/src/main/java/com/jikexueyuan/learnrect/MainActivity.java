package com.jikexueyuan.learnrect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//8.3作业
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//8.5.3自定义皮肤
//        setContentView(new MyView(this));//8.5.2自定义视图属性
    }
}
