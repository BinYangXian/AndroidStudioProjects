package com.jikexueyuan.learnrect;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
//8.5.2自定义视图属性
public class MyView extends View {
    public MyView(Context context) {//提供给代码使用
        super(context);
    }//此构造方法是由代码使用的；

    public MyView(Context context, AttributeSet attrs) {//此构造方法由资源解析程序使用,既是此构造方法由资源解析器来
        // 访问，只要我们把组件（com.jikexueyuan.learnrect.MyView）放在sample_my_view.xml文件里边，系统就会执行此方法；它的
        // 返回值是init方法里的TypedArray类型的a？
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.MyView);//取得declare-styleable的MyView对象的属性数组 ta

        int color=ta.getColor(R.styleable.MyView_rect_color,0xffff0000);//如果MyView_rect_color没有设置color，那么使用默认值为0xffff0000，并返回赋值给color。
        setBackgroundColor(color);//设置R.styleable.MyView_rect_color的默认color为0xffff0000

        ta.recycle();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
