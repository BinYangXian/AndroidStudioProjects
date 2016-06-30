package com.jikexueyuan.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jikexueyuan.startservicefromanotherapp.AppServiceRemoteBinder;
import com.jikexueyuan.startservicefromanotherapp.TimerServiceCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent serviceIntent;
    private EditText etInput;
    private AppServiceRemoteBinder binder = null;//4、客户端aidl回调函数：第3.7步
    private TextView tvCallbackText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.jikexueyuan.startservicefromanotherapp", "com.jikexueyuan.startservicefromanotherapp.AppService"));
        //2、跨应用绑定service：第二步，通过Intent实例传递需要启动的别的app中的service的路径及名字。
        findViewById(R.id.btnStartAppService).setOnClickListener(this);
        findViewById(R.id.btnStopAppService).setOnClickListener(this);
        findViewById(R.id.btnBindAppService).setOnClickListener(this);
        findViewById(R.id.btnUnbindAppService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
        etInput = (EditText) findViewById(R.id.etInput);
        tvCallbackText= (TextView) findViewById(R.id.tvCallbackText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartAppService:
                startService(serviceIntent);
                break;
            case R.id.btnStopAppService:
                stopService(serviceIntent);
                break;
            case R.id.btnBindAppService:
                bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);//2、跨应用绑定service：第三步。
                break;
            case R.id.btnUnbindAppService:
                unbindService(this);
                binder = null;
                break;
            case R.id.btnSync:
                if (binder != null) {
                    try {                  //由于是远程通信，可能有异常，需要捕获
                        binder.setData(etInput.getText().toString());  //3、同步数据第五步，更改data实现同步，完。
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {//2、跨应用绑定service：第四步。
        System.out.println("Service Onbind");
        System.out.println(service);
//       !!!binder= (AppServiceRemoteBinder) service;//由于service是从别的app中传来的AppServiceRemoteBinder实例，
// 它们虽然名字相同，但所占内存地址不同，不是同一个类，所以不能如此造型
        binder = AppServiceRemoteBinder.Stub.asInterface(service); // (3/4)、同步数据/客户端aidl回调函数，第四步，将service传入远程
        // 通讯接口（第一步中创建的）中的asInterface方法！！！
        try {
            binder.registCallback(onServiceCallback);  //4、客户端aidl回调函数：第五步，注册adil回调函数
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {//4、客户端aidl回调函数：第九步，销毁activity时，注销onServiceCallback的注册来断开service链接，完。
        super.onDestroy();
        callUnRegisterBinder();
    }

    private void callUnRegisterBinder() {//解绑方法封装，提取公因式
        try {
            binder.unRegistCallback(onServiceCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {//2、跨应用绑定service：第五步，完。
        callUnRegisterBinder();
    }//4、客户端aidl回调函数：第八步，注销onServiceCallback的注册来断开service链接。

    //4、客户端aidl回调函数：第四步，创建自定义AIDL文件类型接口的onServiceCallback实例，供第五步注册使用
    private TimerServiceCallback.Stub onServiceCallback = new TimerServiceCallback.Stub() {
        @Override
        public void onTimer(int numIndex) throws RemoteException {
            Message message=new Message();
            message.obj=MainActivity.this;  //为了严谨,将当前上下文对象（MainActivity的实例）赋给该message实例
            message.arg1=numIndex;
            handler.sendMessage(message);
        }
    };
    private final MyHandler handler=new MyHandler();//4、客户端aidl回调函数：第六步，创建handler。下一步中启动另外一线程更改UI资源
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {//4、客户端aidl回调函数：第七步。
            super.handleMessage(msg);
            int index=msg.arg1;
            MainActivity _this= (MainActivity) msg.obj;//得到上述消息发送处的上下文对象（MainActivity的实例）
            _this.tvCallbackText.setText("这是回调回服务器的数据"+index);
        }
    }
    //下述为Handler的匿名子类的定义。
//    private Handler handler=new Handler(){
//    @Override
//    public void handleMessage(Message msg) {
//        super.handleMessage(msg);
//        int index=msg.arg1;
//        MainActivity _this= (MainActivity) msg.obj;//为了严谨,指定activity对象
//        _this.tvCallbackText.setText("这是回调回服务器的数据"+index);
//    }
//};
}
