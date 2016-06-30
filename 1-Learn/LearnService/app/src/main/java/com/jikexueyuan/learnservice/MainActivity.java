package com.jikexueyuan.learnservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, MyService.class);//显式Intent
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnBindService).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                startService(intent);
                break;
            case R.id.btnStopService:
                stopService(intent);
                break;
            case R.id.btnBindService:
                bindService(intent,this, Context.BIND_AUTO_CREATE);//当同时启动服务与绑定服务后，单独的停止/解绑不会
                // 使服务停止，只有二者同时执行的时候服务才会执行onDestroy()。
                break;
            case R.id.btnUnBindService:
                unbindService(this);
                break;

        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("service Connected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) { //服务所在进程崩溃或被杀掉的时候执行

    }
}
