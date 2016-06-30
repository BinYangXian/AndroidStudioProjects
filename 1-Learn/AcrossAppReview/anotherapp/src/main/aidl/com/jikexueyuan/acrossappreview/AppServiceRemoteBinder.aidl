// AppServiceRemoteBinder.aidl
package com.jikexueyuan.acrossappreview;

// Declare any non-default types here with import statements
import com.jikexueyuan.acrossappreview.TimerServiceCallback;
interface AppServiceRemoteBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
              void setData(String data);
              void registCallback(TimerServiceCallback callback);  //5、服务端aidl回调函数：第二步，添加注册或注销方法
              void unRegistCallback(TimerServiceCallback callback);//5、服务端aidl回调函数：第一步，添加注册或注销方法
}
