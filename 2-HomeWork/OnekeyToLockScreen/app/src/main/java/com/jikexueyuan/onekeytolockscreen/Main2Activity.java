package com.jikexueyuan.onekeytolockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    private DevicePolicyManager devicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);//获得系统权限
        if (devicePolicyManager.isAdminActive(new ComponentName(this, DeviceManagerBc.class))) {
            devicePolicyManager.lockNow();
            finish();
        } else {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}
