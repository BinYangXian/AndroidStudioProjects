package com.jikexueyuan.connectservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private EditText etData;
    private MyService.Binder binder=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnSyncData).setOnClickListener(this);
        etData = (EditText) findViewById(R.id.etData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                Intent i=new Intent(this,MyService.class);
                i.putExtra("data",etData.getText().toString()); // 法1、启动service并传递数据的方法：第一步
                startService(i);
                break;
            case R.id.btnStopService:
                stopService(new Intent(this, MyService.class));//服务的实例在一个操作系统中只能有一次，所以
                // 就算intent不同，停止的服务也是同一个。（这里的intent只是用来配置信息的）
                break;
            case R.id.btnBindService:
                bindService(new Intent(this, MyService.class), this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                unbindService(this);   //这里只用this
                break;
            case R.id.btnSyncData:
                if (binder!=null){
                   binder.setDate(etData.getText().toString()); //法2、绑定service进行通信：
                   // 建立连接完成后，设置MyService输出字符串同步。（此种方法比startService中通过发送intent来
                   // 同步显得方便高效）
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {  //法2、绑定service进行通信：建立连接第一步,取得第二步中方法的返回值
        binder= (MyService.Binder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
