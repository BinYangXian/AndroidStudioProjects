package com.jikexueyuan.animationlearningsetreview;

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

    private Button button, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                animation = new AlphaAnimation(0, 1);
                animation.setDuration(1000);
                break;
            case R.id.button2:
                animation = AnimationUtils.loadAnimation(this, R.anim.bb);
                break;
            case R.id.button3:
                animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//勿忘
                animation.setDuration(1000);
                break;
            case R.id.button4:
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                break;
            case R.id.button5:
                animation = new TranslateAnimation(0, 100, 0, 100);
                animation.setDuration(1000);
                break;
            case R.id.button6:
                animation = AnimationUtils.loadAnimation(this, R.anim.translate);
                break;
            case R.id.button7:
                animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000);
                break;
            case R.id.button8:
                animation = AnimationUtils.loadAnimation(this, R.anim.scale);
                break;
            case R.id.button9:
                AnimationSet animationSet=new AnimationSet(true);
                animation = new AlphaAnimation(0, 1);
                animation.setDuration(1000);
                animationSet.addAnimation(animation);

                animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000);
                animationSet.addAnimation(animation);

                break;
            case R.id.button10:
                animation = AnimationUtils.loadAnimation(this, R.anim.scale_and_translate);
                break;
            case R.id.button11:
                animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(1000);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                break;
            case R.id.button12:

                break;
        }
        v.startAnimation(animation);
    }
}
