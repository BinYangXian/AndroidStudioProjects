package com.jikexueyuan.onekeytolockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btnLockScreen, btnGetPermission;
    private DevicePolicyManager devicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);//获得系统权限
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btnLockScreen = (Button) findViewById(R.id.btnLockScreen);
        btnGetPermission = (Button) findViewById(R.id.btnGetPermission);
        btnGetPermission.setOnClickListener(this);
        btnLockScreen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetPermission:
                if (!devicePolicyManager.isAdminActive(new ComponentName(this, DeviceManagerBc.class))) {
                    Intent i = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, DeviceManagerBc.class));
                    startActivity(i);
                } else {
                    devicePolicyManager.removeActiveAdmin(new ComponentName(this, DeviceManagerBc.class));
                    hideLockButton();
                }
                break;
            case R.id.btnLockScreen:
                devicePolicyManager.lockNow();
        }
    }

    private void hideLockButton() {
        btnGetPermission.setText("获取设备管理者权限");
        btnLockScreen.setVisibility(View.INVISIBLE);
        btnLockScreen.setEnabled(false);
    }

    private void showLockButton() {
        btnGetPermission.setText("取消设备管理者权限");
        btnLockScreen.setVisibility(View.VISIBLE);
        btnLockScreen.setEnabled(true);
    }

    @Override
    protected void onResume() {//此法是由于点击获取权限按钮时，在上文会启动另外一个activity，
    // 当前activity执行onPause()，回到该activity时执行onResume()
        super.onResume();
        if (devicePolicyManager.isAdminActive(new ComponentName(this, DeviceManagerBc.class))) {
            showLockButton();
        } else {
            hideLockButton();
        }
    }
}
