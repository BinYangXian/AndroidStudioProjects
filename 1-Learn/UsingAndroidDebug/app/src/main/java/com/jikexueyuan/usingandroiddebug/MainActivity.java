package com.jikexueyuan.usingandroiddebug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        People people=new People();
        System.out.println(people.getName());
//        People people=null;
//        System.out.println(people.getName());
    }
}
