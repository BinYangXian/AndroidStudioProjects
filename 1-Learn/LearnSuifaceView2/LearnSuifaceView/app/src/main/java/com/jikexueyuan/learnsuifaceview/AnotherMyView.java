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
//绘制多个图形8.6.3：
public class AnotherMyView extends SurfaceView implements SurfaceHolder.Callback{//其父类 包含一个专门绘图
    // 的surface，我们可以通过它控制像素、大小等;SurfaceHolder可以看出一个控制器。（我们在绘制图形的时候一定是在surface对象被
// 创建之后开始绘制图形，以及图形的渲染，而在surface被销毁之前会结束所有的图形处理，）
    private Paint paint=null;//申明一个画笔
    public AnotherMyView(Context context) {
        super(context);
        paint=new Paint(); //新建画笔
        paint.setColor(Color.RED);//设置画笔颜色
        getHolder().addCallback(this);//回调函数
    }
    //添加绘制方法。
    public void draw() {
        Canvas canvas=getHolder().lockCanvas();//锁定画布（绘制图形前必锁定画布）
        canvas.drawColor(Color.WHITE);//初始化画布
//        canvas.drawRect(0,0,100,100,paint);
        canvas.save();//先保存成我们可编辑状态
        canvas.rotate(90,getHeight()/2,getWidth()/2); //绕中点顺时针旋转90°
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight(),paint);
        canvas.restore();//绘制好之后储存canvas的状态并释放掉旋转动作，才使得第一个图形的旋转不影响下述图形
        canvas.drawLine(0,getHeight()/2+100,getWidth(),getHeight(),paint);

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
