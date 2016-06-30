package com.jikexueyuan.launchlocalapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( );
                intent.setData(Uri.parse("app:"));/*。如果一个intent对象或者过滤器没有指定action。 结果如下第二种 :
                （l 如果一个filter 没有指定任何action ,那么则没有任何intent会被匹配。所以，所有的intent将不会通过此测试。）
                2 另一方面，如果一个intent对象没有指定任何action，那么将自动通过此测试（前提这个过滤器中有至少一个action）：此时只通过<data android:scheme="app" />
                协议匹配activity，与通过浏览器启动的本地activity同理，在app:可加任意字符串信息。*/

                startActivity(intent);
            }
        });
    }
}
