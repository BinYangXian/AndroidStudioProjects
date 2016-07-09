package com.jikexueyuan.animationformovebuttonreview;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by fangc on 2016/4/5.
 */
//自定义动画，可以随意设计我们想要的动画：
public class CoustomAnim extends Animation {
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        t.setAlpha(interpolatedTime);
//        t.getMatrix().setTranslate(200*interpolatedTime,200);
        t.getMatrix().setTranslate((float) (Math.sin(interpolatedTime*100)*20),0);//1、setTranslate方法是瞬间移动矩阵到指定位置
        // 2、interpolatedTime*10/3.14约等于摇头的周期数；第二个*10为摇头的幅度大小
        super.applyTransformation(interpolatedTime, t);
    }
}
