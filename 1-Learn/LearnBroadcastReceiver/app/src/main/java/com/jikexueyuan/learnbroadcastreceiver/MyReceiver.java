package com.jikexueyuan.learnbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.logging.Filter;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }
    //2.第一步
public static final String ACTION="com.jikexueyuan.learnbroadcastreceiver.intent.action.MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("接收到了信息:"+intent.getStringExtra("data"));
        abortBroadcast();
    }
}
