package com.jikexueyuan.dragthepic;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView,moveImage;
    private View rootView;
    private int acitonBarHeight;
    private int notifiHeight;

    private float diffX,diffY;
    private RelativeLayout.LayoutParams downParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView=getLayoutInflater().inflate(R.layout.activity_main, null);
        rootView.post(new Runnable() {//new了一个实现Runnable接口的匿名线程,得以获取acitonBarHeight与notifiHeight
            @Override
            public void run() {
                if (getSupportActionBar()!=null){
                    acitonBarHeight = getSupportActionBar().getHeight();

                    Rect frame=new Rect();
                    getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                    notifiHeight = frame.top;
                }
            }
        });

        imageView= (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionTag= MotionEventCompat.getActionMasked(event);//获取事件的具体动作标志
        int pointerIndex = MotionEventCompat.getActionIndex(event);//获取关联触点事件的索引

        switch (actionTag){
            case MotionEvent.ACTION_DOWN:
                //获取imageView 图标的布局参数（其中也包含尺寸参数用getWidth()与getHeight()获取）
                downParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();

                if (getTouchXPoint(event,pointerIndex)>downParams.leftMargin
                        &&getTouchXPoint(event,pointerIndex)<downParams.leftMargin+imageView.getWidth()
                        &&getTouchYPoint(event, pointerIndex)-acitonBarHeight-notifiHeight>downParams.topMargin
                        &&getTouchYPoint(event,pointerIndex)-acitonBarHeight-notifiHeight<downParams.topMargin+imageView.getHeight()){
                    moveImage=imageView;
                    diffX=getTouchXPoint(event,pointerIndex)-downParams.leftMargin;//diffX = 点击点x轴坐标减去图标左侧边x轴坐标
                    diffY=getTouchYPoint(event, pointerIndex)-downParams.topMargin-acitonBarHeight-notifiHeight;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                if (moveImage!=null){
                    downParams.leftMargin= (int) (getTouchXPoint(event, pointerIndex)-diffX);//击点x轴坐标减去diffX
                    downParams.topMargin= (int) (getTouchYPoint(event, pointerIndex)-acitonBarHeight-notifiHeight-diffY);
                    imageView.setLayoutParams(downParams);
                }

                break;
            case MotionEvent.ACTION_UP:

                moveImage=null;
                break;
        }

        return super.onTouchEvent(event);
    }

    private float getTouchYPoint(MotionEvent event, int pointerIndex) {//自定义方法
        return MotionEventCompat.getY(event, pointerIndex);
    }

    private float getTouchXPoint(MotionEvent event, int pointerIndex) {//自定义方法
        return MotionEventCompat.getX(event, pointerIndex);
    }
}
