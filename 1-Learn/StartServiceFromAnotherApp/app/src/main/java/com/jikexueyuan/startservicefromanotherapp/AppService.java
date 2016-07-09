package com.jikexueyuan.startservicefromanotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class AppService extends Service {
    private String data="默认数据";//3、同步数据第三步，测试用线程 输出data
    private boolean running=false;//3、同步数据第三步，测试用线程 输出data
    private RemoteCallbackList<TimerServiceCallback> callbackList=new RemoteCallbackList<>();
    // 5、aidl回调函数-服务端：第二步、上述为远程回调对象的列表。
    public AppService() {
    }
    public AppService getAppService(){
        return this;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return new AppServiceRemoteBinder.Stub() {  //2、（3、也需要此步）跨应用绑定service：第一步，返回一个远程通信接口的
                                          // IBinder实例，api规定不能直接用AIDL的接口，需使用Stub()存根，其是AppServiceRemoteBinder
                                          // 接口中的内部抽象类，需要复写实现。
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override                               //
            public void setData(String data) throws RemoteException {//3、同步数据：第二步，返回一个带data更改方法的IBinder实例
                AppService.this.data=data;//此处不能通过this直接访问到AppService实例中的data！！！
            }

            @Override
            public void registCallback(TimerServiceCallback callback) throws RemoteException { //此法是被anothreApp通过AppServiceRemoteBinder文件实现的接口回调，
            // 也就是说registCallback抽象方法在另一个app中被调用，而其实现通过AIDL跳转到了本app！！！！//5、aidl回调函数-服务端：
                callbackList.register(callback);
            }

            @Override
            public void unRegistCallback(TimerServiceCallback callback) throws RemoteException { //5、aidl回调函数-服务端：
                callbackList.unregister(callback);
            }


        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        running=true;
        System.out.println("Service Started");
        new Thread(){                //3、同步数据第二步，测试用线程 输出data
            @Override
            public void run() {
                super.run();
                for (numIndex=0;  running; numIndex++) {
                    System.out.println(numIndex);
                    int count=callbackList.beginBroadcast();//准备广播 回调列表中的所有实例
                    while (count-->0){//先判断，再自减
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
//                while (running){   //5、aidl回调函数-服务端：第一步、更换线程内容为上述
//
//                    System.out.println(data);
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }.start();
    }
private int numIndex=0;
    @Override
    public void onDestroy() {
        running=false;
        super.onDestroy();

        System.out.println("Service destory");
    }

}
