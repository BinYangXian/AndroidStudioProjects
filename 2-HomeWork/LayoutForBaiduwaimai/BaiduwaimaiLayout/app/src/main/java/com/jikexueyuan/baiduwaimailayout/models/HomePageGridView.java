package com.jikexueyuan.baiduwaimailayout.models;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by fangc on 2016/3/3.
 */
public class HomePageGridView extends GridView {
    public HomePageGridView(Context context) {
        super(context);
    }

    public HomePageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePageGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
