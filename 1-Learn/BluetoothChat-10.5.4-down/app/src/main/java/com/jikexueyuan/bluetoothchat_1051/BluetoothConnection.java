package com.jikexueyuan.bluetoothchat_1051;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by fangc on 2016/4/21.
 */
public class BluetoothConnection {
    private static final String NAME = "BluetoothChat";
    private static final UUID MY_UUID = UUID.fromString("0e103a09-cdce-4096-afe2-3b0ff01f3a86");//通过新建一个
    // java项目创建了一个UUID(标识码，我们当前应用的连接都使用此)。
    private final Activity context;
    private boolean listenning = true; // TODO: 2016/4/22 fasle?
    private AcceptThread acceptThread = null;

    public BluetoothConnection(Activity context) {
        this.context = context;
    }

    public void startServerSocket() {
        if (acceptThread == null) {  //如果已经存在了，就不重复开启线程侦听了
            acceptThread = new AcceptThread();
            acceptThread.start();
        }
    }

    public void stopServerSocket() {
        if (acceptThread != null) {
            acceptThread.cancel();
            acceptThread = null;
        }
    }

    class ManagerConnectionThread extends Thread { //创建一个新的线程来管理连接
        private BluetoothSocket socket;

        public ManagerConnectionThread(BluetoothSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();
            // TODO: 2016/4/22
        }
    }

    class AcceptThread extends Thread {  //内部类
        private BluetoothAdapter bluetoothAdapter;
        private BluetoothServerSocket serverSocket = null;

        public AcceptThread() {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            try {//添加自动生成的UUID后，可能发生IO错误(异常后，serverSocket还是为空)。
                listenning = true;
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(BluetoothConnection.NAME, BluetoothConnection.MY_UUID);//
                //自定义的参数2。唯一标识，用来标识当前应用间的连接的。
                System.out.println("Success to listen.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            super.run();

            if (serverSocket == null) {
                return;
            }
            BluetoothSocket socket = null;
            while (listenning) { //当前serverSocket处于侦听状态的时候，循环执行；否则停掉此。
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listenning) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "无法接受连接", Toast.LENGTH_SHORT).show();//这里如直接Toast会试着在非UI线程中
                                // 更改UI线程来显示Toast的，有违规定会异常！所以用runOnUiThread方法
                            }
                        });

                    }
                }
                if (socket != null) {
                    new ManagerConnectionThread(socket).start();//当端口不为空时(侦听成功)，创建管理连接的线程;
                    cancel();//同时把当前的serverSocket给关闭掉，因为蓝牙的同一个通道（listenUsingRfcommWithServiceRecord）
                    // 只能接受一个连接，不像tcp/ip能接受多个连接。（此时作为服务器启用的功能已经不再需要）
                }
            }
        }

        public void cancel() {
            if (serverSocket == null) {
                return;
            }
            listenning = false;
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
