package com.jikexueyuan.connectservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    private boolean running=false;
    private String data ="这是默认信息";

    public MyService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data =intent.getStringExtra("data");                    // 法1、启动service并传递数据的方法：第二步
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {    //法2、绑定service进行通信：建立连接第二步，这里的返回值，是返回给第一步中作为参数的
        return new Binder();
    }
    public class Binder extends android.os.Binder{ //法2、绑定service进行通信：建立连接第三步,
    // 创建一个MyService的内部公开的public类Binder，通过类中公开的方法，以便其它链接的应用使用
        public void setDate(String data){
            MyService.this.data =data;           //写一个公开的方法，通过此法修改MyService中data的值
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        running=true;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (running){
                    try {
                        System.out.println(data);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running=false;
    }
}
