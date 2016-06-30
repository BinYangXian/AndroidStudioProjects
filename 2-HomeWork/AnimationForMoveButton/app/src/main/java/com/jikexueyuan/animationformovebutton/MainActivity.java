package com.jikexueyuan.animationformovebutton;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button, button2, button3, button4;
    private ImageView imageView;
    private int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        imageView = (ImageView) findViewById(R.id.imageView);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    private void buttonMoveByViewAnimationXML(final View v) {

        Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate1);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation b = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate2);
                v.startAnimation(b);
            }
        });
        v.startAnimation(a);

    }

    private void button2MoveByViewAnimationCode(final View v) {
        final TranslateAnimation moveA = new TranslateAnimation(0, 200, 0, 0);
        moveA.setDuration(500);
        final TranslateAnimation moveB = new TranslateAnimation(200, 200, 0, 200);
        moveB.setDuration(500);
        moveA.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.startAnimation(moveB);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(moveA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                buttonMoveByViewAnimationXML(v);
                break;
            case R.id.button2:
                button2MoveByViewAnimationCode(v);
                break;
            case R.id.button3:
                button3MoveByObjectAnimatorXML(v);
                break;
            case R.id.button4:
                button4MoveByObjectAnimatorCode(v);
                break;
            case R.id.imageView:
                flag = -flag;
                v.animate().rotationYBy(-180 * flag).setDuration(1000).start();//多次点击会导致图片倾斜，既Y轴非0°/180°
                break;
        }
    }

    private void button3MoveByObjectAnimatorXML(View v) {
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.translate);
        animator.setTarget(v);
        animator.start();
    }

    private void button4MoveByObjectAnimatorCode(View v) {
        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
        set.playSequentially(ObjectAnimator.ofFloat(v, "translationX", 0, 200), ObjectAnimator.ofFloat(v, "translationY", 0, 200), ObjectAnimator.ofFloat(v, "translationY", 200, 0), ObjectAnimator.ofFloat(v, "translationX", 200, 0));
        set.start();
    }
}