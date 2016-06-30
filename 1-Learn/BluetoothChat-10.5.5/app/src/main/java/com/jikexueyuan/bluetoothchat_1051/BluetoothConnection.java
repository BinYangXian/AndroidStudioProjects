package com.jikexueyuan.bluetoothchat_1051;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by fangc on 2016/4/21. Connecting Devices：
 * 为了在两台设备上创建一个连接，你必须实现服务器端和客户端两头的机制，因为一个设备必须打开一个服务器socket，
 * 而另一个设备初始化创建（使用服务器设备的MAC地址来初始化一个连接）。当他们在相同的RFCOMM通道上有一个已连接的
 * BluetoothSocket 时，服务器和客户被认为是互相连接了。这时，每一个设备可以包含输入和输出流，而且可以开始数据传输，
 * 这在Managing a Connection课程中将会讨论。本节课描述了怎样在两台设备之间初始化连接。
 * <p/>
 * 服务器设备和客户端设备使用不同的方法来得到需要的 BluetoothSocket 。服务器在接受外来的连接的将会接收到它。
 * 客户端在向服务器端打开一个RFCOMM通道时会接收到它。
 */

/*注：About UUID
一个全局唯一的标识符（UUID）是一个标准的128-bit格式的string ID，它被用于唯一标识信息。
一个UUID的关键点是它非常大以至于你可以随机选择而不会发生崩溃。在这种情况下，
它被用于唯一地指定你的应用中的蓝牙服务。为了得到一个UUID以在你的应用中使用，
你可以使用网络上的任何一种随机UUID产生器，然后使用 fromString(String)初始化一个UUID。*/
public class BluetoothConnection {
    private static final String NAME = "BluetoothChat";
    private static final UUID MY_UUID = UUID.fromString("0e103a09-cdce-4096-afe2-3b0ff01f3a86");//通过新建一个
    // java项目创建了一个UUID(标识码，我们当前应用的连接都使用此)。
    private final Activity context;
    private boolean listenning = true; // TODO: 2016/4/22 fasle?
    private AcceptThread acceptThread = null;
    private ManageConnectionThread manageConnectionThread = null;
    private onReadNewLineListener onReadNewLineListener = null;

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

    public void manageConnection(BluetoothSocket socket) { //这里不用判断了，因为我们现在只处理一个连接，所以直接这样写就行了。
        manageConnectionThread = new ManageConnectionThread(socket);
        manageConnectionThread.start();//当端口不为空时(侦听成功)，创建管理连接的线程;10.5.5的内容
    }

    public BluetoothConnection.onReadNewLineListener getOnReadNewLineListener() {
        return onReadNewLineListener;
    }

    public void setOnReadNewLineListener(BluetoothConnection.onReadNewLineListener onReadNewLineListener) {
        this.onReadNewLineListener = onReadNewLineListener;
    }

    private void sendLine(String line){
        if (manageConnectionThread!=null){
            manageConnectionThread.sendLine(line);
        }
    }

    class ManageConnectionThread extends Thread { //AcceptThread侦听成功时，创建一个新的线程来管理连接
        private BluetoothSocket socket;
        private InputStream in;
        private OutputStream out;

        public ManageConnectionThread(BluetoothSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            super.run();

            try {
                out = socket.getOutputStream();
                in = socket.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;

                while ((line = br.readLine()) != null) {
                    if (getOnReadNewLineListener()!=null){
                        getOnReadNewLineListener().onRead(line); //通过接口通知给外界
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            cancel();

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "已经断开连接", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void cancel() {
            try {
                socket.close();
                manageConnectionThread=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendLine(String line) {
            line+="\n"; //由于我们读取的时候也是一行一行读取的
            try {
                out.write(line.getBytes("UTF-8")); //写出数据
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class AcceptThread extends Thread {  //内部类
        private BluetoothAdapter bluetoothAdapter;
        private BluetoothServerSocket serverSocket = null;

        /*创建服务器一、用 listenUsingRfcommWithServiceRecord(String, UUID)得到一个BluetoothServerSocket。
        这个String是你的服务的标志名称，系统将会把它写入设备中的一个新的服务发现协议（SDP）
        数据库条目中（名字是任意的，并且可以只是你应用的名字）。UUID同样被包含在SDP条目中，
        并且将会成为和客户端设备连接协议的基础。也就是说，当客户端尝试连接这个设备时，
        它将会携带一个UUID用于唯一指定它想要连接的服务器。这些UUIDs必须匹配以便该连接可以被接受（在下一步中）。
         通过调用accept()开始监听连接请求。*/
        public AcceptThread() {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            try {//添加自动生成的UUID后，可能发生IO错误(异常后，serverSocket还是为空)。
                listenning = true;
                serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(BluetoothConnection.NAME, BluetoothConnection.MY_UUID);//
                //服务器在接受外来的连接的将会接收到一个BluetoothSocket？自定义的参数2。唯一标识，用来标识当前应用间的连接的。
                System.out.println("Success to listen.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*创建服务器二、serverSocket通过调用accept()开始监听连接请求。
        这一个阻塞调用。在一个连接被接受或一个异常出现时，它将会返回。只有当一个远程设备使用一个UUID
        发送了一个连接请求，并且该UUID和正在监听的服务器socket注册的UUID相匹配时，一个连接才会被接受。
        成功后，accept() 将会返回一个已连接的 BluetoothSocket。*/
        @Override
        public void run() {
            super.run();

            if (serverSocket == null) {
                return;
            }
            BluetoothSocket socket = null;
            while (listenning) { //当前serverSocket处于侦听状态的时候，循环接收；否则停掉此。
                try {
                    socket = serverSocket.accept();//客户端在向服务器端发送UUID后打开一个RFCOMM通道时会接收到该蓝牙通道。
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
                    manageConnection(socket);
                    cancel();//同时把当前的serverSocket给关闭掉，因为蓝牙的同一个通道（listenUsingRfcommWithServiceRecord）
                    // 只能接受一个连接，不像tcp/ip能接受多个连接。（此时作为服务器启用的功能已经不再需要）
                }
            }
        }

        /*创建服务器三、cancel()中调用close()，除非你想要接受更多的连接。
        这将释放服务器socket和它所有的资源，但是不会关闭 accept()返回的已连接的 BluetoothSocket。不同于TCP/IP，
        RFCOMM仅仅允许每一个通道上在某一时刻只有一个已连接的客户端，
        因此在大多数情况下在接受一个已连接的socket后，在BluetoothServerSocket上调用 close() 是非常必要的。*/
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

    interface onReadNewLineListener { //通过此设置时间监听器，来通知外界
        void onRead(String line);
    }
}
