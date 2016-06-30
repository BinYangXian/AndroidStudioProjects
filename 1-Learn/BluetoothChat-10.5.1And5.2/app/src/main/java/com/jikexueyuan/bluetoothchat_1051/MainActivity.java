package com.jikexueyuan.bluetoothchat_1051;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BLUETOOTH = 1000;
    private BluetoothAdapter bluetoothAdapter;
    private ListView lvDevices;
    private DevicesListAdapter devicesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvDevices= (ListView) findViewById(R.id.lvDevices);
        devicesListAdapter=new DevicesListAdapter(this,android.R.layout.simple_list_item_1);
        lvDevices.setAdapter(devicesListAdapter);

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
                                finish(); //如果用户先点 否，后又点击了 关闭，那么人家现在就不想开这个程序了哦，那就全关闭吧！
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
            devicesListAdapter.add(new BluetoothDeviceWrapper(device));//遍历并添加到自定义的devicesListAdapter中的
            // items里，显然此处的adapter（适配器）应有存储数据的功能
        }
    }

    public void btnDiscoverableClicked(View view) {
        Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,120);//参数2.可被发现的时长
        startActivity(i);
    }

}
