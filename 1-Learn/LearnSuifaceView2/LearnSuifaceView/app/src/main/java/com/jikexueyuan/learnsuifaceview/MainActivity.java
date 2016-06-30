package com.jikexueyuan.learnsuifaceview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//SurfaceView主要用于arg？游戏的开发；View是通过UI主线程直接更新我们的画面，UI主线程首先要响应我们用户的操作（比如
// 事件的分发等等），然后再更新画面，此种模式当主线程任务繁重时会导致UI主线程的阻塞，导致无法响应用户操作扽等问题；
// 而surfaceView不会出现此情况，它是直接从内存或dma等硬件取得图形图像的数据，而且重要的是它还可以通过主线程之外的
// 线程直接更新画面；说到游戏开发，我们会根据画面的需求来选择主动更新与被动更新，如果是被动更新（例如棋盘类的游戏
// 需要有触发事件，这种时候完全可以采用View来处理），而主动更新（如旋转罗盘）时，就需要一个线程去处理，应采用
// surfaceView。
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new MyView(this));//绘制单个图形8.6.1与8.6.2：
//        setContentView(new AnotherMyView(this));//绘制多个图形8.6.3：
        setContentView(new GameView(this));//绘制多个图形8.6.4：
    }
}
