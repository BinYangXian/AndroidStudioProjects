package com.jikexueyuan.onekeylockscreenreview;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAddDevicePolicy,btnLockScreen;
    private DevicePolicyManager devicePolicyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicePolicyManager= (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);

        btnAddDevicePolicy= (Button) findViewById(R.id.btnAddDevicePolicy);
        btnLockScreen= (Button) findViewById(R.id.btnLockScreen);
        btnAddDevicePolicy.setOnClickListener(this);
        btnLockScreen.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddDevicePolicy:
                if (devicePolicyManager.isAdminActive(new ComponentName(this,DeviceManagerBC.class))){
                    devicePolicyManager.removeActiveAdmin(new ComponentName(this,DeviceManagerBC.class));
                    btnAddDevicePolicy.setText("获取锁屏权限");
                    btnLockScreen.setVisibility(View.INVISIBLE);
                }else{
                    Intent i=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,new ComponentName(this,DeviceManagerBC.class));
                    startActivity(i);
                }
                break;
            case R.id.btnLockScreen:
                devicePolicyManager.lockNow();
                break;
        }
    }

    @Override
    protected void onResume() {
        if (devicePolicyManager.isAdminActive(new ComponentName(this,DeviceManagerBC.class))){
            btnAddDevicePolicy.setText("取消锁屏权限");
            btnLockScreen.setVisibility(View.VISIBLE);
        }else {
            btnLockScreen.setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }
}
