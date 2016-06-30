package com.jikexueyuan.sendargs2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TheAty extends AppCompatActivity {
    private TextView tv;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_aty);
        editText= (EditText) findViewById(R.id.editText);
        Intent i=getIntent();
////        Bundle date=i.getExtras();//获取主activity的信息包
//        Bundle date=i.getBundleExtra("information");
        tv=(TextView)findViewById(R.id.tv);//自动实例化tv
////        tv.setText(i.getStringExtra("date"));
//        User user= (User) i.getSerializableExtra("user");
        User user=i.getParcelableExtra("user");
        tv.setText(String.format("User info(name=%s,age=%d)",user.getName(),user.getAge()));
//        tv.setText(String.format("name=%s,age=%d,name1=%s",date.getString("name"),date.getInt("age"),date.getString("name1", "leo")));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent();
                a.putExtra("date",editText.getText().toString());
                setResult(1,a);
                finish();
            }
        });
    }
}
