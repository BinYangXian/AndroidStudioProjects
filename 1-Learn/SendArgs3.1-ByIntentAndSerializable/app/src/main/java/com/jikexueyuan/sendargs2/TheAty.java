package com.jikexueyuan.sendargs2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TheAty extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_aty);
        Intent i=getIntent();
////        Bundle date=i.getExtras();//获取主activity的信息包
//        Bundle date=i.getBundleExtra("information");
        tv=(TextView)findViewById(R.id.abc);//自动实例化tv
////        tv.setText(i.getStringExtra("date"));
        User user= (User) i.getSerializableExtra("user");
        tv.setText(String.format("User info(name=%s,age=%d)",user.getName(),user.getAge()));
//        tv.setText(String.format("name=%s,age=%d,name1=%s",date.getString("name"),date.getInt("age"),date.getString("name1", "leo")));
    }
}
