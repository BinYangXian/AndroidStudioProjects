package com.jikexueyuan.learncontext;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv=new TextView(this);
        imageView=new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        tv.setText(R.string.app_name);
        setContentView(imageView);
        System.out.println(getResources().getText(R.string.app_name));
    }
}
