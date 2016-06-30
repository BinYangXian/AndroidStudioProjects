package com.jikexueyuan.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jikexueyuan.acrossappcommunication.AppServiceRemoteBinder;
import com.jikexueyuan.acrossappcommunication.TimerServiceCallback;
//该类，可以完全复制远程app中的MainActivity。
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private TextView serviceText;
    private AppServiceRemoteBinder binder;  //信息通讯桥梁之关键！！而回调函数是信息载体！！
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnBindService).setOnClickListener(this);
        findViewById(R.id.btnUnbindService).setOnClickListener(this);
        serviceText = (TextView) findViewById(R.id.serviceText);
        //4、第四步，Intent可以用来启动activity、service，绑定service。
        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.jikexueyuan.acrossappcommunication", "com.jikexueyuan.acrossappcommunication.MyService"));//不要忘了指定传入参数！！！！！！！！！！

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBindService:
                //4、客户端aidl回调函数：第0步，绑定
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindService:
                try {   //众多判断为了消除bug与异常
                    if (binder!=null)
                        binder.unRegistCallback(onServiceCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                finally {
                    if (binder!=null){
                        unbindService(this);
                    }
                    binder = null;
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = AppServiceRemoteBinder.Stub.asInterface(service);
        try {
            binder.registCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private TimerServiceCallback.Stub onServiceCallback = new TimerServiceCallback.Stub() {
        @Override
        public void onTimer(int numIndex) throws RemoteException {
            Message msg = new Message();
            msg.obj = MainActivity.this;
            msg.arg1 = numIndex;
            handler.sendMessage(msg);
        }
    };

    private MyHandler handler = new MyHandler();//4、客户端aidl回调函数：第六步，创建handler。下一步中启动另外一线程更改UI资源

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {//4、客户端aidl回调函数：第七步。
            super.handleMessage(msg);
            MainActivity _this = (MainActivity) msg.obj;//为了严谨,指定activity对象
            int index = msg.arg1;
            _this.serviceText.setText("current number is:" + index);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        try {
            binder.unRegistCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {   //众多判断为了消除bug与异常
            if (binder!=null)
                binder.unRegistCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        finally {
            if (binder!=null){
                unbindService(this);
            }
            binder = null;
        }
    }

}
