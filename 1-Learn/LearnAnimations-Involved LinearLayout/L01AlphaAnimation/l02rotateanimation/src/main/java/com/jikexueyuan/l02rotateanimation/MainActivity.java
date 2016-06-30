package com.jikexueyuan.l02rotateanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

public class MainActivity extends AppCompatActivity {

    private RotateAnimation ra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//1、代码配置旋转参数
//        ra = new RotateAnimation(0, 360,100,50);
        ra=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(1000);

        findViewById(R.id.btnRotateMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(ra);
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.ra));
            }
        });
    }
}
