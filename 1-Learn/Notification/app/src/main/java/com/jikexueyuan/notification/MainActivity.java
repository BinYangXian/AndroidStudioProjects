package com.jikexueyuan.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private int counter=0;
    public static final int NOTIFICATION_ID=1200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                NotificationCompat.Builder builder=new NotificationCompat.Builder(MainActivity.this);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle("操，你有"+counter+"个新的消息");
                builder.setContentText("fuck you");

                Notification notification=builder.build(); //当builder为通知添加的属性足够的时候，进行下述真实对象的创建
                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//
                // 用此法显示通知
                manager.notify(NOTIFICATION_ID,notification);  //notify构造方法，参数1.标注此notification的id为1200，
                // 参数2.关联在上文创建的notification为此id。
            }
        });
    }
}
