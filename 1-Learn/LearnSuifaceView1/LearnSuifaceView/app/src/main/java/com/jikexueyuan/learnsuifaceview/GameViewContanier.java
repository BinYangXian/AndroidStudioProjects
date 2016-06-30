package com.jikexueyuan.learnsuifaceview;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangc on 2016/2/23.
 */
//绘制多个图形8.6.4：
//由于GameView是一个组合图形，所以需要创建一个图形容器(此处当作集合使用)，更为方便、易读、理解。
public class GameViewContanier {
    private List<GameViewContanier> chileren=null;
    private float x=0,y=0;//组合图形的移动8.6.5


    public GameViewContanier() {
        chileren=new ArrayList<GameViewContanier>();//初始化图形容器
    }

    public void childrenView(Canvas canvas){//因为本类这是容器类，所以需要承载其他的图形View,这里提供给子类一个复写的
    // 方法，这里传进来一个绘制方法的对象canvas。
    }
    public void drawGameView(Canvas canvas){//绘制容器chileren内所有的View类
        canvas.save();//组合图形的移动8.6.5
        canvas.translate(getX(), getY());//移动。组合图形的移动8.6.5
        childrenView(canvas);//当chileren集合中没有绘制方法对象时，此处调用的是父类中的childrenView方法。
        System.out.println("cildrenView执行次数");
        for (GameViewContanier c :chileren) {//此处c代表chileren集合里的各个具体图形绘制<方法>的实例！有了方法实例后，
        // 通过c（方法实例）调用drawGameView方法时，才会执行到子类中的childrenView方法。
            c.drawGameView(canvas);
        }
        canvas.restore();//组合图形的移动8.6.5
    }
    public void addChildrenView(GameViewContanier child){//添加子View到集合的方法
        chileren.add(child);
    }
    public void removeChilerenView(GameViewContanier child){//移除子View的方法
        chileren.remove(child);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
