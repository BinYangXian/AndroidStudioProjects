package com.jikexueyuan.learnrotatingrect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class RotatingRectView extends View {
    private Paint p;//my defined 控制图形呈现的画笔对象
    private float degrees=0;
    public RotatingRectView(Context context) {
        super(context);
        initProperties();
    }

    public RotatingRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initProperties();
    }

    public RotatingRectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initProperties();
    }

    private void initProperties() {
        // Load attributes
        p=new Paint();
        p.setColor(Color.RED);
    }
    Handler handler;
    @Override
    protected void onDraw(Canvas canvas) {//运用canvas可以绘制图形
        super.onDraw(canvas);

        //Draw the my rect:
        canvas.save();//绘制之前保存canvas的状态(可编辑状态)
        canvas.translate(200, 200);//平移其中心点
        canvas.rotate(degrees, 50, 50);//保存后让它旋转一个角度、中心点.
        canvas.drawRect(0, 0, 100, 100, p);

        degrees++;
        canvas.restore();//绘制之后保存canvas的状态

        invalidate();//绘制出图形后立即重绘，（比较耗用资源，可以补充Handler省一部分资源）
//        handler=new Handler();
//        handler.sendEmptyMessageDelayed();//延时绘制
    }
}
