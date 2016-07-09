package com.jikexueyuan.animationlearningsetreview;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by fangc on 2016/7/4.
 */
public class CoustomAnim extends Animation {
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
    }
}
