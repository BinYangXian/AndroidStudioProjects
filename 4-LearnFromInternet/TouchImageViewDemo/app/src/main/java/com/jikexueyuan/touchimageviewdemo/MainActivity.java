package com.jikexueyuan.touchimageviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by fangc on 2016/3/16.
 */
public class MainActivity extends Activity {
    private MyZoomImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        MyZoomImageView imgView = (MyZoomImageView) findViewById(R.id.img_id);
        imgView.initImageView(dm.widthPixels, dm.heightPixels - 80);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}
