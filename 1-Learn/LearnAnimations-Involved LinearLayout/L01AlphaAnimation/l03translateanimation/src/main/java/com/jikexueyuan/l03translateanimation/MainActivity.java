package com.jikexueyuan.l03translateanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

public class MainActivity extends AppCompatActivity {

    private TranslateAnimation ta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//代码实现移动动画效果
        ta = new TranslateAnimation(0, 200, 0, 200);//0是指相对于ta对象当前位置，x/y 200是向右与向下的像素增量
        ta.setDuration(1000);

        findViewById(R.id.btnTranslateMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                v.startAnimation(ta);
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.ta));
            }
        });
    }
}
