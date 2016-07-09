package com.jikexueyuan.learnrotatingrect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
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
        p=new Paint(); //画笔对象
        p.setColor(Color.RED);//设画笔对象的color为红色属性
    }
    Handler handler;
    @Override
    protected void onDraw(Canvas canvas) {//运用canvas可以绘制图形
        super.onDraw(canvas);
/*当我们对画布进行旋转，缩放，平移等操作的时候其实我们是想对特定的元素进行操作，比如图片，一个矩形等，但是当你用canvas的方法来进行这些操作的时候，
其实是对整个画布进行了操作，那么之后在画布上的元素都会受到影响，所以我们在操作之前调用canvas.save()来保存画布当前的状态，当操作之后取出之前保存过的状态，
这样就不会对其他的元素进行影响。---http://blog.csdn.net/oney139/article/details/8143281 */
        //Draw the my rect:
        canvas.save();//绘制之前保存canvas的状态(可编辑状态)
        canvas.translate(200, 200);//平移其中心点
        canvas.rotate(degrees, 50, 50);//保存后让它旋转一个角度、中心点.
        canvas.drawRect(0, 0, 100, 100, p);

        degrees++;
        canvas.restore();//移除之前save保存canvas的状态

        invalidate();//绘制出图形后立即重绘，（比较耗用资源，可以补充Handler进行延时绘制省系统资源）
//        handler=new Handler();
//        handler.sendEmptyMessageDelayed();//延时绘制
    }
}
