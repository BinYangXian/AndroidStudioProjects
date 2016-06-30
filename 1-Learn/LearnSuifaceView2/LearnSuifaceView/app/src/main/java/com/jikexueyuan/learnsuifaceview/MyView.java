package com.jikexueyuan.learnsuifaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by fangc on 2016/2/23.
 */
//绘制单个图形8.6.1与8.6.2：
public class MyView extends SurfaceView implements SurfaceHolder.Callback{//其父类 包含一个专门绘图
// 的surface，我们可以通过它控制像素、大小等;SurfaceHolder可以看出一个控制器。（我们在绘制图形的时候一定是在surface对象被
// 创建之后开始绘制图形，以及图形的渲染，而在surface被销毁之前会结束所有的图形处理，）
    private Paint paint=null;//申明一个画笔
    public MyView(Context context) {
        super(context);
        paint=new Paint(); //新建画笔
        paint.setColor(Color.RED);//设置画笔颜色
        getHolder().addCallback(this);//回调函数
    }
//添加绘制方法。
    public void draw() {
        Canvas canvas=getHolder().lockCanvas();//锁定画布（绘制图形前必锁定画布）
        canvas.drawColor(Color.WHITE);//初始化画布
        canvas.drawRect(0,0,100,100,paint);
        getHolder().unlockCanvasAndPost(canvas);//解锁画布（绘制图形后必解锁画布）
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
