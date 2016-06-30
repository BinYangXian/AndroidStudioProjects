package com.jikexueyuan.startlocalactivityfromweb;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LocalActivity extends AppCompatActivity {
    final static String ACTION="com.jikexueyuan.startlocalactivityfromweb.intent.action.LocalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        Uri uri=getIntent().getData();
        System.out.println(uri);
    }
}
