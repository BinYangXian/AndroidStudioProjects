package com.jikexueyuan.acrossappreview;

import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;


/**
 * Created by fangc on 2016/2/2.
 */
public class MyService extends Service {
    private String data = "这是默认信息";
    private boolean running = false;
    private String str = "";
    RemoteCallbackList<TimerServiceCallback> callbackList=new RemoteCallbackList<>();
    @Override
    public IBinder onBind(Intent intent) {
        return new AppServiceRemoteBinder.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
                MyService.this.data = aString;
            }

            @Override
            public void setData(String data) throws RemoteException {
                MyService.this.data = data;
            }

            @Override
            public void registCallback(TimerServiceCallback callback) throws RemoteException {
                registCallback(callback);
            }

            @Override
            public void unRegistCallback(TimerServiceCallback callback) throws RemoteException {
                unRegistCallback(callback);
            }

        };
    }


//    public class Binder extends android.os.Binder {
//        public void setData(String data) {
//            MyService.this.data = data;
//        }
//        public MyService getService(){
//            return MyService.this;
//        }
//    }

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
                    int count=callbackList.beginBroadcast();
                    while (count-->0){
                        callbackList.beginBroadcast();
                        if (callBack != null)
                            callBack.onDateChange(str);
                        try {
                            callbackList.getBroadcastItem(count).onTimer(i);
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

    private ICallBack callBack = null;//初值设置为null

    public ICallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public interface ICallBack {
        void onDateChange(String data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service on Destroy");
        running = false;
    }
}
