// TimerServiceCallback.aidl
package com.jikexueyuan.startservicefromanotherapp;

// Declare any non-default types here with import statements

interface TimerServiceCallback {  //5、服务端aidl回调函数：第二步，创建包含回调函数的接口

    void onTimer(int numIndex);//当回调函数的时候，我们的实验是将int类型的参数传递出来

}
