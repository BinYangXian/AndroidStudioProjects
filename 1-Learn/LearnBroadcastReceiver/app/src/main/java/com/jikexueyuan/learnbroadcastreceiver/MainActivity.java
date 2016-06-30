package com.jikexueyuan.learnbroadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnSendMessage).setOnClickListener(this);
        findViewById(R.id.btnReg).setOnClickListener(this);
        findViewById(R.id.btnUnreg).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendMessage:
//       Intent intent=new Intent(new Intent(this,MyReceiver.class)); //显式intent！配合manifest自动添加
                          // 的eceiver，可以直接从activity发送消息到继承自BroadcastReceiver的MyReceiver。
                Intent intent=new Intent(MyReceiver.ACTION);         //2.第四步，隐式intent！！！！！
                intent.putExtra("data","要有风儿 要有肉 要有美女 要有酒");
                sendOrderedBroadcast(intent,null); //3.
                break;
            case R.id.btnReg:
            if (receiver==null){
                receiver=new MyReceiver();
                registerReceiver(receiver,new IntentFilter(MyReceiver.ACTION));//2.第二步
            }

                break;
            case R.id.btnUnreg:
                if (receiver!=null) {
                    unregisterReceiver(receiver);//2.第三步
                    receiver=null;
                }
                break;
        }
    }
    private MyReceiver receiver=null;
}
