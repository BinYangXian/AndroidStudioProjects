package com.jikexueyuan.learnsuifaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fangc on 2016/2/23.
 */
//绘制多个图形8.6.4 and 组合图形的移动8.6.5
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameViewContanier contanier;
    private GameViewRect rect;
    private GameViewCircle circle;
    public GameView(Context context) {
        super(context);
        contanier=new GameViewContanier();
        rect=new GameViewRect();
        circle=new GameViewCircle();
        contanier.addChildrenView(rect);
        contanier.addChildrenView(circle);
        getHolder().addCallback(this);//回调函数
    }

    public void draw() {
        Canvas canvas=getHolder().lockCanvas();//锁定画布（绘制图形前必锁定画布）
        canvas.drawColor(Color.WHITE);//初始化画布
        contanier.drawGameView(canvas);  //通过contanier中的drawGameView方法，绘制出所有添加到contanier内的图形。
        getHolder().unlockCanvasAndPost(canvas);//解锁画布（绘制图形后必解锁画布）
    }

    private Timer timer=null;//系统的。组合图形的移动：8.6.5
    private TimerTask task=null;

    private void startTimer(){//组合图形的移动：8.6.5
        timer=new Timer();
        task=new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        };
        timer.schedule(task,100,100);//每隔100毫秒执行一次
    }
    private void stopTimer(){//组合图形的移动：8.6.5
        if (timer!=null){
            timer.cancel();
            timer=null;
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startTimer();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopTimer();
    }
}
