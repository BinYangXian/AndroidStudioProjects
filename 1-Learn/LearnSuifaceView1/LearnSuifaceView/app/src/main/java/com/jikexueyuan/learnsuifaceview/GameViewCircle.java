package com.jikexueyuan.learnsuifaceview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by fangc on 2016/2/23.
 */
//绘制多个图形8.6.4：
public class GameViewCircle extends GameViewContanier {
    private Paint paint=null;

    public GameViewCircle() {
        paint=new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void childrenView(Canvas canvas) {
        super.childrenView(canvas);
        canvas.drawCircle(50,50,50,paint);
        this.setY(this.getY()+1);
    }
}
