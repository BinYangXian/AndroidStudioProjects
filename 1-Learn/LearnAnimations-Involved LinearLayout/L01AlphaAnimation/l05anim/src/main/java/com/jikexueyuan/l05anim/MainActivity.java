package com.jikexueyuan.l05anim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //添加两个动画效果
    private AnimationSet as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//代码添加混合动画效果，第一步为关键
        as = new AnimationSet(true);//是否共用动画补间：如匀速执行/加速执行/减速执行。
        as.setDuration(1000);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        as.addAnimation(aa);

        TranslateAnimation ta = new TranslateAnimation(200, 0, 200, 0);
        ta.setDuration(1000);
        as.addAnimation(ta);

        findViewById(R.id.btnAnimateMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(as);//代码设置启动混合效果
//动画效果的侦听：
//                v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim));//xml文件配置启动混合效果
                Animation a=AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim);
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Toast.makeText(MainActivity.this,"Animation end",Toast.LENGTH_SHORT).show();
                    }
                });
                v.startAnimation(a);
            }
        });
    }
}
