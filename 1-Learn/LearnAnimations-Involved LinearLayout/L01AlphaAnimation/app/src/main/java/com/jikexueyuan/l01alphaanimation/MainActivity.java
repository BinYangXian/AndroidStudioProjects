package com.jikexueyuan.l01alphaanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //代码配置动画效果
                AlphaAnimation aa=new AlphaAnimation(0,1);
                aa.setDuration(1000);
                v.startAnimation(aa);
                //利用aa.xml文件配置
                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.aa));
            }
        });
    }
}
