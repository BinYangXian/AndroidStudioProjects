package com.jikexueyuan.keepscreenon;

import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private PowerManager pm;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm= (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Wake");//参数2是标记
    }

    @Override
    protected void onResume() {
        super.onResume();

        wakeLock.acquire();//启用wakeLock
    }

    @Override
    protected void onPause() {
        super.onPause();

        wakeLock.release();//释放wakeLock
    }
}
