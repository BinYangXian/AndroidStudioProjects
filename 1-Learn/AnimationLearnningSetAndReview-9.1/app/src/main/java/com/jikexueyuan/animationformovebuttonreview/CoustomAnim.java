package com.jikexueyuan.animationformovebuttonreview;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by fangc on 2016/4/5.
 */
//自定义动画
public class CoustomAnim extends Animation {
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        t.setAlpha(interpolatedTime);
//        t.getMatrix().setTranslate(200*interpolatedTime,200);
        t.getMatrix().setTranslate((float) (Math.sin(interpolatedTime*10)*10),0);
        super.applyTransformation(interpolatedTime, t);
    }
}
