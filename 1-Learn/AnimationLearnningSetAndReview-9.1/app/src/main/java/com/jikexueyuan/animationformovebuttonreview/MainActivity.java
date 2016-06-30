package com.jikexueyuan.animationformovebuttonreview;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button button2;
    private Button button3;
    private Button button4,button5,button6,button7,button8,button9,button10,button11,button12;
    private Animation animation;
    private ObjectAnimator animator;
    private int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= (Button) findViewById(R.id.button);
        button2= (Button) findViewById(R.id.button2);
        button3= (Button) findViewById(R.id.button3);
        button4= (Button) findViewById(R.id.button4);
        button5= (Button) findViewById(R.id.button5);
        button6= (Button) findViewById(R.id.button6);
        button7= (Button) findViewById(R.id.button7);
        button8= (Button) findViewById(R.id.button8);
        button9= (Button) findViewById(R.id.button9);
        button10= (Button) findViewById(R.id.button10);
        button11= (Button) findViewById(R.id.button11);
        button12= (Button) findViewById(R.id.button12);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag==1){
                    animator=ObjectAnimator.ofFloat(v,"rotationY",0,180);
                }else {
                    animator=ObjectAnimator.ofFloat(v,"rotationY",180,0);
                }
                animator.setDuration(1000);
                animator.start();
                flag=-flag;
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            //按键1-10为试图动画
            case R.id.button://代码配置
                animation=new AlphaAnimation(0,1);
                animation.setDuration(1000);
                v.startAnimation(animation);
                break;
            case R.id.button2://xml配置
                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.aa));
                break;
            case R.id.button3://代码配置
                animation=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(1000);
                v.startAnimation(animation);
                break;
            case R.id.button4://xml配置
                v.startAnimation(AnimationUtils.loadAnimation(this,R.anim.rotating));
                break;
            case R.id.button5://代码配置
                animation=new TranslateAnimation(0,100,0,100);
                animation.setDuration(1000);
                v.startAnimation(animation);
                break;
            case R.id.button6://xml配置
                v.startAnimation(AnimationUtils.loadAnimation(this,R.anim.translating));
                break;
            case R.id.button7:
                //组合动画，代码配置
                AnimationSet animationSet=new AnimationSet(true);
                animationSet.setDuration(1000);

                animation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(1000);
                animationSet.addAnimation(animation);

                animation=new TranslateAnimation(0,100,0,100);
                animation.setDuration(1000);
                animationSet.addAnimation(animation);

                v.startAnimation(animationSet);
                break;
            case R.id.button8:
                //延时组合动画，xml配置
                v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scaling));
                break;
            case R.id.button9:
                //通过动画的监听实现，连续不同动画效果；代码配置
                animation=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(1000);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animation=new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                        animation.setDuration(1000);
                        v.startAnimation(animation);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                v.startAnimation(animation);
                break;
            case R.id.button10:
                //自定义 按键摇头
                animation=new CoustomAnim();
                animation.setDuration(1000);
                v.startAnimation(animation);
                break;
            case R.id.button11:
                //AnimatorSet（属性动画集合），代码配置
                AnimatorSet animatorSet=new AnimatorSet();
                animatorSet.setDuration(2000);
                animatorSet.playSequentially(ObjectAnimator.ofFloat(v, "translationX", 0, 100)
                        , ObjectAnimator.ofFloat(v, "translationY", 0, 100)
                        , ObjectAnimator.ofFloat(v, "translationY", 100, 0)
                        , ObjectAnimator.ofFloat(v, "translationX", 100, 0));
                animatorSet.start();
                break;
            case R.id.button12:
                //AnimatorSet（属性动画集合），xml配置
AnimatorSet set= (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this,R.animator.move_button);
                set.setTarget(v);
                set.start();
                break;
        }
    }
}
