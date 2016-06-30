package com.jikexueyuan.onekeylockscreenreview;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AnotherLauncherActivity extends AppCompatActivity {
    private DevicePolicyManager devicePolicyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devicePolicyManager= (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        if (devicePolicyManager.isAdminActive(new ComponentName(this,DeviceManagerBC.class))){
            devicePolicyManager.lockNow();

        }else {
            startActivity(new Intent(this,MainActivity.class));
        }
        finish();
    }

}
