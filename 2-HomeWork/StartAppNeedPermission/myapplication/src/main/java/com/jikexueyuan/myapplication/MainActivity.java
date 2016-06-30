package com.jikexueyuan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent("com.jikexueyuan.startappneedpermission.intent.action.Main2Activity"));
                } catch (SecurityException e) {
//                    System.out.println("启动Main2Activity，需要相应权限");
                    Toast.makeText(MainActivity.this, "启动Main2Activity，需要相应权限", Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.btnRigisterPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              MainActivity.this.requestPermissions("com.jikexueyuan.startappneedpermission.permission.Main2Activity",null);
            }
        });
    }
}
