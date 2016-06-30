package com.jikexueyuan.connectservicereview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Created by fangc on 2016/2/2.
 */
public class MyService extends Service {
    private String data = "这是默认信息";
    private boolean running = false;
    private String str = "";

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    public class Binder extends android.os.Binder {
        public void setData(String data) {
            MyService.this.data = data;
        }
        public MyService getService(){//外界通过该方法来添加事件的绑定
            return MyService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service on Create");
        new Thread() {
            @Override
            public void run() {
                super.run();
                running = true;
                int i = 0;
                while (running) {
                    i++;
                    str = data + i;
                    System.out.println(str);
                    if (callBack!=null)
                    callBack.onDateChange(str);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private ICallBack callBack=null;//初值设置为null

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }
    public interface ICallBack{
    void onDateChange(String data);
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service on Destroy");
        running = false;
    }
}
