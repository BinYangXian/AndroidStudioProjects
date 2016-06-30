package com.jikexueyuan.l04scaleanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

public class MainActivity extends AppCompatActivity {

    private ScaleAnimation sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//代码实现缩放动画
//        sa = new ScaleAnimation(0, 1, 0, 1);//0与1表示缩放比例从无到百分百,默认缩放中心是从最左。
        sa=new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(1000);

        findViewById(R.id.scale_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                v.startAnimation(sa);
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.sa));
            }
        });
    }
}
