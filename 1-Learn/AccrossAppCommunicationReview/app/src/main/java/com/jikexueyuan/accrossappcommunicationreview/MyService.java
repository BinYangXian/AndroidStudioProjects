package com.jikexueyuan.accrossappcommunicationreview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by fangc on 2016/2/2.
 */
public class MyService extends Service{
    private boolean running=false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                int i=0;
                running=true;
                i++;
                while (running){
                    System.out.println(i);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running=false;
    }
}
