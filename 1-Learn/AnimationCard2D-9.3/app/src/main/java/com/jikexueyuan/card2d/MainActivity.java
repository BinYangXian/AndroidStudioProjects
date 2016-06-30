package com.jikexueyuan.card2d;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageA,imageB;

    private ScaleAnimation sato0 =new ScaleAnimation(1,0,1,1, Animation.RELATIVE_TO_PARENT,0.5f,
            Animation.RELATIVE_TO_PARENT,0.5f);
    private ScaleAnimation sato1=new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_PARENT,0.5f,
            Animation.RELATIVE_TO_PARENT,0.5f);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //增加加速与减速效果增加模拟2D真实度：
        sato0.setInterpolator(new AccelerateInterpolator());
        sato1.setInterpolator(new DecelerateInterpolator());
        //给最外层的父级控件加一个监听事件，当我们点击到最外层的时候，就让它进行动画的翻转;先初始化
        initView();
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (imageA.getVisibility()==View.VISIBLE){
                   imageA.startAnimation(sato0);
               }else {
                   imageB.startAnimation(sato0);
               }
            }
        });
    }

    private void initView() {
        imageA= (ImageView) findViewById(R.id.ivA);
        imageB= (ImageView) findViewById(R.id.ivB);
        sato0.setDuration(500);
        sato1.setDuration(500);
        showImageA();

        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (imageA.getVisibility()==View.VISIBLE){
                    imageA.setAnimation(null);
                    showImageB();
                    imageB.startAnimation(sato1);
                }else{
                    imageB.setAnimation(null);
                    showImageA();
                    imageA.startAnimation(sato1);
                }
            }
        });
    }

    private void showImageB() {
        imageB.setVisibility(View.VISIBLE);
        imageA.setVisibility(View.INVISIBLE);//占位隐藏
    }

    private void showImageA() {
        imageA.setVisibility(View.VISIBLE);
        imageB.setVisibility(View.INVISIBLE);//占位隐藏
    }
}
