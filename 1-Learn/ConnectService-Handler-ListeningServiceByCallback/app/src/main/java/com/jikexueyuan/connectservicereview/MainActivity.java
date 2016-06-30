package com.jikexueyuan.connectservicereview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private EditText etData;
    private Intent intent;
    private MyService.Binder binder;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        findViewById(R.id.btnStopService).setOnClickListener(this);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        findViewById(R.id.btnSyncData).setOnClickListener(this);
        etData = (EditText) findViewById(R.id.etData);
        intent = new Intent(MainActivity.this, MyService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:

                intent.putExtra("data", etData.getText().toString());
                startService(intent);
                break;
            case R.id.btnStopService:
                stopService(intent);
                break;
            case R.id.btnBindService:
                bindService(intent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                unbindService(this);
                break;
            case R.id.btnSyncData:
                binder.setData(etData.getText().toString());
                break;
        }
    }
    /*service 的作用：保持socket连接、网络连接、注册/控制广播、后台检测应用程序等 比较耗时、不需要用户可见；
    其是基于线程（cpu资源调度的基本单位）的应用组件，可以做一些线程很难实现的事情，二者几乎没有关系*/

        /*回掉函数法：MyService中的方法主动循环发出服务内部数据变化，
          原理：此接口类型的ICallBack的实例化的实体在MainActivity中实现实例化，MyService中的onDateChange方法
          被循环调用时，显然其实体中的代码也会循环执行咯（同时将MyService中的str传递出来）！*/
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (MyService.Binder) service;
        binder.getService().setCallBack( new MyService.ICallBack() {
            @Override
            public void onDateChange(String data) {//直接使用一个新创建的线程来执行UI线程的资源的话，是不行的，是android的
            // 安全机制，UI线程是不允许其他辅助线程来修改其资源的；此处需要Handler。
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("data",data);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv.setText(msg.getData().getString("data"));
        }
    };
    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
