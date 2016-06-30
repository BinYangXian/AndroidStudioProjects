package com.jikexueyuan.anotherapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartAppThatNeedPermission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setComponent(new ComponentName("com.jikexueyuan.startappneedthecustompermission","com.jikexueyuan.startappneedthecustompermission.MainActivity"));
                startActivity(intent);
            }
        });
    }
}