package com.jikexueyuan.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private boolean serviceRunning=false;
    public MyService(){

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new Binder();//此处需要返回一个实现了IBinder接口的对象
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//该方法会在每次服务启动的时候执行一次，
    // 而onCreate()只会执行第一次（因为一个服务只会创建一次）。
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceRunning=true;
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (serviceRunning) {
                    System.out.println("服务正在运行...");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        System.out.println("service create");   //该输出只在service启动的时候输出一次，在当前Activity启动服务不会重复执行该onCreate方法
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceRunning=false;
        System.out.println("service destory");
    }
}
