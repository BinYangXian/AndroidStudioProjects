package com.jikexueyuan.bluetoothchat_1051;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, BluetoothConnection.onReadNewLineListener {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1000;
    private BluetoothAdapter bluetoothAdapter;
    private ListView lvDevices;
    private DevicesListAdapter devicesListAdapter;
    private boolean scanning = false;
    private View viewProgress;
    private BluetoothConnection connection;
    private EditText etInput;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection=new BluetoothConnection(this);
        connection.setOnReadNewLineListener(this);

        viewProgress = findViewById(R.id.viewProgress);

        lvDevices = (ListView) findViewById(R.id.lvDevices);
        devicesListAdapter = new DevicesListAdapter(this, android.R.layout.simple_list_item_1);
        lvDevices.setAdapter(devicesListAdapter);
        lvDevices.setOnItemClickListener(this);

        //find edit text
        etInput= (EditText) findViewById(R.id.etChatInput);
        tvOutput= (TextView) findViewById(R.id.tvChatOutput);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "你的设备不支持蓝牙", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                requestEnableBlueTooth();
            } else {
                loadBondedDevices();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_ENABLE_BLUETOOTH:
                switch (resultCode) {
                    case RESULT_OK:
                        loadBondedDevices();
                        break;
                    default:
                        new AlertDialog.Builder(this).setTitle("提醒").setMessage("你拒绝启动蓝牙").setPositiveButton("再次启用", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestEnableBlueTooth();
                            }
                        }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setCancelable(false).show();//为防止用户误操作（默认情况，点击对话框之外的地方会使对话框关闭）
                        // ，所以设置可取消状态为否，让对话框一直show()。
                        break;
                }
                break;
        }
    }

    void requestEnableBlueTooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
    }

    void loadBondedDevices() {
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();//获得系统存储的已配对设备
        for (BluetoothDevice device :
                bondedDevices) {
            devicesListAdapter.add(new BluetoothDeviceWrapper(device));//遍历添加到自定义的devicesListAdapter去
        }
    }

    public void btnDiscoverableClicked(View view) {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);//可发现的时长
        startActivity(i);
    }

    public void btnStartScanClicked(View view) {
        checkToScanDevices();
    }

    @Override
    protected void onResume() {
        super.onResume();

        connection.startServerSocket(); //开启服务端的代码
    }

    @Override
    protected void onPause() {//当activity处于后台时候，停止搜索以释放系统资源
        super.onPause();

        checkToStopScanDevices();

        connection.stopServerSocket();
    }

    public void btnStopScanClicked(View view) {
        checkToStopScanDevices();
    }

    private void checkToScanDevices() {
        if (!scanning) {
            registerReceiver(deviceFoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));//
            // 由于我们注册的intentFilter仅有ACTION_FOUND，所以在onReceive中省去判断是否与intent.getAction相等
            bluetoothAdapter.startDiscovery();

            showProgress();

            scanning = true;
        }
    }

    private void checkToStopScanDevices() {
        if (scanning) {
            unregisterReceiver(deviceFoundReceiver);
            bluetoothAdapter.startDiscovery();  // TODO: 2016/4/21 视频讲的是stop什么的，但是写的是这个，与启用方法一样

            hideProgress();

            scanning = false;
        }
    }

    void showProgress() {
        viewProgress.setVisibility(View.VISIBLE);

    }

    void hideProgress() {
        viewProgress.setVisibility(View.GONE);
    }

    private BroadcastReceiver deviceFoundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction()))//省略，由于我们注册的intentFilter
// 仅有ACTION_FOUND一项，所以无需判断。
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            devicesListAdapter.add(new BluetoothDeviceWrapper(device));
        }
    };

    public void btnSendLineClicked(View view) { //用xml文件中的代码调用
        if (!TextUtils.isEmpty(etInput.getText())){
            connection.sendLine(etInput.getText().toString());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        connection.connect(devicesListAdapter.getItem(position).getDevice());//连接另一个设备
    }

    @Override
    public void onRead(final String line, final BluetoothDevice remoteDevice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvOutput.append(remoteDevice.getName()+":"+line+"\n");
            }
        });
    }
}
