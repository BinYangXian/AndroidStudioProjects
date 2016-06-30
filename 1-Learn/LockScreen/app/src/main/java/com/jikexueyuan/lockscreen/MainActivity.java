package com.jikexueyuan.lockscreen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DevicePolicyManager devicePolicyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicePolicyManager= (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);//获得系统权限
        findViewById(R.id.btnLockScreen).setOnClickListener(this);
        findViewById(R.id.btnRegDeviceAdmin).setOnClickListener(this);
        findViewById(R.id.btnUnregDeviceAdmin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLockScreen:
                devicePolicyManager.lockNow();
                break;
            case R.id.btnRegDeviceAdmin:
                Intent i=new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);//参数为action
                i.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this, DeviceManagerBc.class));
                                                     //参数2为广播接收器，注意与参数1及上述参数action的关系
                startActivity(i);//通过这个系统组件所 添加的 这个面板 来实现注册成为系统组件的功能！!
                break;
            case R.id.btnUnregDeviceAdmin:
                devicePolicyManager.removeActiveAdmin(new ComponentName(this,DeviceManagerBc.class));
                break;
        }
    }
}
