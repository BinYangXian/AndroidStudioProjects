package com.jikexueyuan.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jikexueyuan.acrossappreview.AppServiceRemoteBinder;
import com.jikexueyuan.acrossappreview.TimerServiceCallback;
public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private EditText etData;
    private Intent intent;
    private AppServiceRemoteBinder binder = null;
    private TextView tvCallbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCallbackText = (TextView) findViewById(R.id.tvCallbackText);
        findViewById(R.id.btnStartAppService).setOnClickListener(this);
        findViewById(R.id.btnStopAppService).setOnClickListener(this);
        findViewById(R.id.btnBindAppService).setOnClickListener(this);
        findViewById(R.id.btnUnbindAppService).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
        etData = (EditText) findViewById(R.id.etInput);
        intent = new Intent();
        intent.setComponent(new ComponentName("com.jikexueyuan.acrossappreview", "com.jikexueyuan.acrossappreview.MyService"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartAppService:
                intent.putExtra("data", etData.getText().toString());
//                startService(intent);
                break;
            case R.id.btnStopAppService:
//                stopService(intent);
                break;
            case R.id.btnBindAppService:

                bindService(intent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindAppService:
                if (binder!=null)
                unbindService(this);
                binder = null;
                break;
            case R.id.btnSync:
                if (binder != null) {
                    try {
                        binder.setData(etData.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = AppServiceRemoteBinder.Stub.asInterface(service);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvCallbackText.setText(msg.getData().getString("data"));
        }
    };

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
