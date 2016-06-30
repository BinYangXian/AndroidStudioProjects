package com.jikexueyuan.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by fangc on 2016/3/13.
 */
public class DeviceManagerBc extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);

        Toast.makeText(context,"已经注册成为系统组件",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);

//        System.out.println("已经注销了系统组件");
        Toast.makeText(context,"已经注销了系统组件",Toast.LENGTH_SHORT ).show();
    }
}
