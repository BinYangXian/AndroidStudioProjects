package com.jikexueyuan.l07customanim;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by fangc on 2016/3/4.
 */
public class CustomAnim extends Animation {

    private int symble=1;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {


        super.initialize(width, height, parentWidth, parentHeight);
    }

    /*
    * 参数1、补间时间：从0变化到1秒的时候applyTransformation方法会反复多次执行；
    * 参数2、变化对象：通过此t对目标组件的状态进行修改。（而当rotate时候需要知道目标对象的中心点，前提是目标对象
    * 的宽与高，所以需要上述initialize方法）*/
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

//        t.getMatrix().setTranslate(200,200);                                  //直接移动到此坐标
//        t.getMatrix().setTranslate(200*interpolatedTime,200*interpolatedTime);//慢慢移动到此坐标
        symble=-symble;
        t.getMatrix().setTranslate((float) Math.sin(interpolatedTime*20)*50,0);/*sin语法
        Math.sin(x)，参数x 必需。一个以弧度表示的角。interpolatedTime*20表示弧度从0变到20，返回值周期
        变化的频率会因为乘数的增大而增加。返回值为 参数 x 的正弦值。返回值在 -1.0 到 1.0 之间。*/


        super.applyTransformation(interpolatedTime, t);
    }

}
