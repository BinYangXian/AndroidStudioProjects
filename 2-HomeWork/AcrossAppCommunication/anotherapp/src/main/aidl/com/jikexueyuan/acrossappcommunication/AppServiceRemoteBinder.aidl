// IMyAidlInterface.aidl
package com.jikexueyuan.acrossappcommunication;

// Declare any non-default types here with import statements
import com.jikexueyuan.acrossappcommunication.TimerServiceCallback;
interface AppServiceRemoteBinder {    //2、第二步，完全拷贝app中的aidl文件作为通讯桥梁的接口

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
//    void setData(String data); //3、第0步，同步数据所需步骤
    void registCallback(TimerServiceCallback callback);  //4、客户端aidl回调函数：第三步，添加注册或注销方法
    void unRegistCallback(TimerServiceCallback callback);//4、客户端aidl回调函数：第三步，添加注册或注销方法
}