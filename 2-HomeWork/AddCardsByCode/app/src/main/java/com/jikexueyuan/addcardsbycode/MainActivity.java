package com.jikexueyuan.addcardsbycode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

//用代码控制子对象
public class MainActivity extends AppCompatActivity {
    private LinearLayout  rootMain,rootHorizontal;
    private Button btnClickme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootMain = new LinearLayout(this);
        rootMain.setOrientation(LinearLayout.VERTICAL);
        setContentView(rootMain);


        for (int k = 0; k < 5; k++) {

            rootHorizontal = new LinearLayout(this);
            rootHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams lpHorizontal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            lpHorizontal.weight = 1;

            for (int j = 0; j < 4; j++) {
                btnClickme = new Button(this);
                btnClickme.setText("" + (4 * k + j + 1));
                btnClickme.setBackgroundColor(Color.RED);
                btnClickme.setTextColor(Color.WHITE);
                LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
              lpButton.leftMargin=10;
                lpButton.rightMargin=10;
                lpButton.topMargin=10;
                lpButton.bottomMargin=10;
                lpButton.weight = 1;

                rootHorizontal.addView(btnClickme, lpButton);
            }
            rootMain.addView(rootHorizontal,lpHorizontal);
        }
    }

}