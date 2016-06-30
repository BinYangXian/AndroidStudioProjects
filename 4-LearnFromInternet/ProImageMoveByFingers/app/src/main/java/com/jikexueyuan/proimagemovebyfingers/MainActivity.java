package com.jikexueyuan.proimagemovebyfingers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity {


    private LinearLayout ll_viewArea;
    private LinearLayout.LayoutParams parm;
    private ViewArea viewArea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ll_viewArea = (LinearLayout) findViewById(R.id.ll_viewArea);
        parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        viewArea = new ViewArea(MainActivity.this, R.drawable.ima1);    //自定义布局控件，用来初始化并存放自定义imageView
        ll_viewArea.addView(viewArea, parm);

    }

//这段代码中要注意的问题是去掉title和状态栏两句代码必须放到 setContentView(R.layout.main);话的前面。而且这两句话必须有，因为
// 后面计算回弹距离是根据全屏计算的（我的i9000就是480x800），如果不去掉title和状态栏，后面的回弹会有误差，总是回弹不到想要的位置。

    //下面看看ViewArea.java文件。就是用来存放和初始化自定义imageView的地方。将来的自定义ImageView被限制在其内部移动缩放。
    class ViewArea extends FrameLayout {  //前面说了ViewArea是一个布局， 所以这里当然要继承一个布局了。LinearLayout也可以
        private int imgDisplayW;
        private int imgDisplayH;
        private int imgW;
        private int imgH;
        private TouchView touchView;
        private DisplayMetrics dm;

        //resId为图片资源id
        public ViewArea(Context context, int resId) { //第二个参数是图片的资源ID，当然也可以用别的方式获取图片
/*        dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        imgDisplayW = dm.widthPixels;
        imgDisplayH = dm.heightPixels;*/ //这种方式获取的屏幕大小和下面的方式结果是一样的，都是480x800（i9000分辨率）
            super(context);
            imgDisplayW = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();//这里的宽高要和xml中的LinearLayout大小一致，如果
            // 要指定大小。xml中 LinearLayout的宽高一定要用px像素单位，因为这里的宽高是像素，用dp会有误差！
            imgDisplayH = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();

            touchView = new TouchView(context, imgDisplayW, imgDisplayH);//这句就是我们的自定义ImageView
            touchView.setImageResource(resId);//给我们的自定义imageView设置要显示的图片
            Bitmap img = BitmapFactory.decodeResource(context.getResources(), resId);
            imgW = img.getWidth();
            imgH = img.getHeight();
            //图片第一次加载进来，判断图片大小从而确定第一次图片的显示方式。(比较图片与屏幕的宽高，取其中较小值)
            int layout_w = imgW > imgDisplayW ? imgDisplayW : imgW;
            int layout_h = imgH > imgDisplayH ? imgDisplayH : imgH;

//下面的代码是判断图片 初始 显示样式的，当然可以根据你的想法随意显示，我这里是将宽大于高的图片按照宽缩小的比例把高压缩，前提必须是宽度超出了屏幕宽，相反，
// 如果高大于宽，我将图片按照高缩小的比例把宽压缩，前提必须是高度超出了屏幕高
            if (imgW >= imgH) {
                if (layout_w == imgDisplayW) {//结合67行，意思就是当宽度超出了屏幕宽
                    layout_h = (int) (imgH * ((float) imgDisplayW / imgW));
                }
            } else {
                if (layout_h == imgDisplayH) {
                    layout_w = (int) (imgW * ((float) imgDisplayH / imgH));
                }
            }
//这里需要注意的是，采用FreamLayout或者LinearLayout的好处是，如果压缩后的图片扔有一个边大于屏幕，那么只显示在屏幕内的部分，可以
// 通过移动后看见外部（不会裁剪掉图片），如果采用RelativeLayout布局，图片会始终完整显示在屏幕内部，不会有超出屏幕的现象。如果
// 图片不是完全占满屏幕，那么在屏幕上没有图片的地方拖动，图片也会移动，这样的体验不太好，建议用FreamLayout或者LinearLayout。
            touchView.setLayoutParams(new FrameLayout.LayoutParams(layout_w, layout_h));//这是自定义imageView的大小，也就是触摸范围
            this.addView(touchView);
        }

    }

}
