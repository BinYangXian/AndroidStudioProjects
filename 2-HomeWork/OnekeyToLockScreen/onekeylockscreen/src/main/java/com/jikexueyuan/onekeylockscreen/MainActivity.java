package com.jikexueyuan.onekeylockscreen;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.jikexueyuan.onekeytolockscreen"
                , "com.jikexueyuan.onekeytolockscreen.MainActivity"));
        i.addFlags(1);
        startActivity(i);
        finish();
    }
}
