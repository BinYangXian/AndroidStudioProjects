package com.jikexueyuan.onekeylockscreenreview;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by fangc on 2016/4/10.
 */
public class DeviceManagerBC extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context,"获取锁屏权限",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Toast.makeText(context,"取消锁屏权限",Toast.LENGTH_SHORT).show();
    }
}
