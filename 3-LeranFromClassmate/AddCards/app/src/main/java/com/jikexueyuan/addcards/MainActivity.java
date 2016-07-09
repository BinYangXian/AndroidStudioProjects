package com.jikexueyuan.addcards;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

//用代码控制子对象
public class MainActivity extends AppCompatActivity  {
    private RelativeLayout layoutOne;

    private int screenWidth, screenHeight,width,height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutOne = new RelativeLayout(this);
        setContentView(layoutOne);

        int statusBarHeight = getStatusBarHeight();

        WindowManager wm = this.getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        height = screenHeight - statusBarHeight;
        width = screenWidth;

        int i = 1;
        for (int k = 0; k < 5; k++) {
            for (int j = 0; j < 4; j++) {
                Button btnClick = new Button(this);
                btnClick.setText("" + (i++));
                btnClick.setBackgroundColor(Color.RED);
                btnClick.setTextColor(Color.WHITE);
                btnClick.setX((width - 100) / 4 * j + 20 * (j + 1));
                btnClick.setY((height - 120) / 5 * k + 20 * (k + 1));

                RelativeLayout.LayoutParams myRlpTwo = new RelativeLayout.LayoutParams((width - 100) / 4, (height - 120) / 5);
                layoutOne.addView(btnClick, myRlpTwo);
            }
        }
        i = 0;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}