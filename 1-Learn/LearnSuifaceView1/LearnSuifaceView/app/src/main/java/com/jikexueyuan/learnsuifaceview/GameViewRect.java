package com.jikexueyuan.learnsuifaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by fangc on 2016/2/23.
 */
//绘制多个图形8.6.4：
public class GameViewRect extends GameViewContanier {
    private Paint paint=null;

    public GameViewRect() {
        paint=new Paint();        //创建画笔
        paint.setColor(Color.RED);//初始化画笔颜色
    }

    @Override
    public void childrenView(Canvas canvas) { //各个子类自定义（重写覆盖父类的）绘制方法，当然需要画布作为参数传入。
        super.childrenView(canvas);
        canvas.drawRect(0,0,100,100,paint);
        this.setY(this.getY()+1);
    }
}
