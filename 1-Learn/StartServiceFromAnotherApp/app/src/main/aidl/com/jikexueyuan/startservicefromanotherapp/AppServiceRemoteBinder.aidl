// AppServiceRemoteBinder.aidl
package com.jikexueyuan.startservicefromanotherapp;

// Declare any non-default types here with import statements
import com.jikexueyuan.startservicefromanotherapp.TimerServiceCallback;//5、服务端aidl回调函数：第三步，引入回调函数所在文件的全路径
interface AppServiceRemoteBinder {    //2、第二步，完全拷贝app中的aidl文件作为通讯桥梁的接口

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void setData(String data);
    void registCallback(TimerServiceCallback callback);  //5、服务端aidl回调函数：第二步，添加注册或注销方法
    void unRegistCallback(TimerServiceCallback callback);//5、服务端aidl回调函数：第一步，添加注册或注销方法
}
