// TimerServiceCallback.aidl
package com.jikexueyuan.startservicefromanotherapp;

// Declare any non-default types here with import statements

interface TimerServiceCallback {  //4、客户端aidl回调函数：第一步，创建包含onTimer方法的接口作为回调函数

    void onTimer(int numIndex);//当回调函数的时候，我们的实验是将int类型的参数传递出来

}
