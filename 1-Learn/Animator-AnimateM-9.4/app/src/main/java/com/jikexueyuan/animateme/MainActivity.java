package com.jikexueyuan.animateme;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnAnimateMe).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //1、代码配置属性动画:
//        v.animate().rotation(360).setDuration(1000).start();//此法简单粗暴，可定制化程度不高
       //2、xml文件配置属性动画:
//        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animte_me);
//        animator.setTarget(v);
//        animator.start();
        //3、xml文件配置属性动画集合:
//        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.animte_me);
//        animator.setTarget(v);
//        animator.start();
        //4、代码配置属性动画集合:
        ObjectAnimator.ofFloat(v,"rotation",0,90,90,360).setDuration(1000).start(); //两个90角度，
        // 属于关键帧的概念，会在第二个执行时停顿一下.
        AnimatorSet set=new AnimatorSet();
        set.setDuration(2000);
        set.playSequentially(ObjectAnimator.ofFloat(v,"alpha",0,1),ObjectAnimator.ofFloat(v,"translationY",0,200));
        set.start();
    }
}
