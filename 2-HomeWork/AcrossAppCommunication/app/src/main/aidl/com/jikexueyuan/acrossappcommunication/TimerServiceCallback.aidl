// IMyAidlInterfaceForCommunicateOtherApp.aidl
package com.jikexueyuan.acrossappcommunication;

// Declare any non-default types here with import statements

interface TimerServiceCallback {
    void onTimer(int numIndex);//当回调函数的时候，我们的实验是将int类型的参数传递出来
}
