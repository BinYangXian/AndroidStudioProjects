package com.jikexueyuan.l01layoutanimationlinearlayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

/**
 * A placeholder fragment containing a simple view.
 */
//3 为布局添加动画效果，包括GridView、ListView等都可用此法，因为它们都是View的子类。
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout rootView= (LinearLayout) inflater.inflate(R.layout.fragment_main, container, false);

        ScaleAnimation sa=new ScaleAnimation(0,1,0,1);
        sa.setDuration(5000);

        LayoutAnimationController lac=new LayoutAnimationController(sa,0.5f);//参数2、为duration值*0.5，此为实际延时长度
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);//setOrder：设置呈现顺序

        rootView.setLayoutAnimation(lac);
        return rootView;
    }
}
