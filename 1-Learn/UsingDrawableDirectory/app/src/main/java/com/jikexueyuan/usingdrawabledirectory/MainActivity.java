package com.jikexueyuan.usingdrawabledirectory;

import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private boolean isRun;
    private ClipDrawable clipDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //缩放图片：
//        imageView = (ImageView) findViewById(R.id.my_scale_drawable_ima);
//        ScaleDrawable scaleDrawable= (ScaleDrawable) imageView.getDrawable();
//        scaleDrawable.setLevel(1);//参数为0时候，没有显示

//    从下往上卷帘式 10秒 显示美女全图,上
                imageView = (ImageView) findViewById(R.id.ima);
        clipDrawable = (ClipDrawable) imageView.getDrawable();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                isRun = true;
                try {
                    while (isRun) {
                       clipDrawable.setLevel(clipDrawable.getLevel() + 1);//非UI主线程不能直接更改UI，需要Handler
                        Thread.sleep(1);
                        handler.sendEmptyMessage(0);//这里不需要使用message，随意传为0
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

//从下往上卷帘式 10秒 显示美女全图,中
   private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            clipDrawable.setLevel(clipDrawable.getLevel() + 1);
            if (clipDrawable.getLevel() == 10000) {
                isRun = false;
            }
        }
    };

    @Override
    protected void onDestroy() {
        isRun = false;
        super.onDestroy();
   }
}
