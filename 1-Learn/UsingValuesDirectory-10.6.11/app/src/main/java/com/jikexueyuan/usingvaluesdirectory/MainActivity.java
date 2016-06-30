package com.jikexueyuan.usingvaluesdirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getResources().getColor(R.color.color4,null);//这样就可以获取到颜色资源

//        getResources().getDimension(R.dimen.viewWith);//这样就可以获取到尺寸资源

//        getResources().getString(R.string.hello);//这样就可以获取到strings的资源
    }
}
