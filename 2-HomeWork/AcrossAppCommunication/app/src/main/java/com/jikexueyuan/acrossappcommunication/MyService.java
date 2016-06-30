package com.jikexueyuan.acrossappcommunication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class MyService extends Service {
    private int numIndex;

    public MyService() {
    }

    private RemoteCallbackList<TimerServiceCallback> callbackList = new RemoteCallbackList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return new AppServiceRemoteBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void registCallback(TimerServiceCallback callback) throws RemoteException {
                callbackList.register(callback);
            }

            @Override
            public void unRegistCallback(TimerServiceCallback callback) throws RemoteException {
                callbackList.unregister(callback);
            }

        };
    }


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service Started");
        running = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (numIndex = 0; running; numIndex++) {
                    System.out.println(numIndex);
                    int count = callbackList.beginBroadcast();//这个count是怎么工作的，不用讲太深，
                    // 你知道的告诉我就行了
                    while (count-- > 0) {
                        try {
                            callbackList.getBroadcastItem(count).onTimer(numIndex);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    callbackList.finishBroadcast();
                    try {
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
        System.out.println("Service destroied");
        running = false;
    }

    private boolean running = false;
}
