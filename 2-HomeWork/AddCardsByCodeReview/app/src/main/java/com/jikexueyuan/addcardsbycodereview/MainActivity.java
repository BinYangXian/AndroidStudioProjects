package com.jikexueyuan.addcardsbycodereview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layout,rootHorizontal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        layout=new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);//勿忘
        setContentView(layout);//勿忘

        for (int k=0;k<5;k++){
            rootHorizontal=new LinearLayout(this);
            rootHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            //勿忘，当lpHorizontal.weight=1;官方建议设置 LinearLayout.LayoutParams的高度为0
            LinearLayout.LayoutParams lpHorizontal=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0);

            for (int i=1;i<5;i++){
                Button button=new Button(this);
                button.setText(4 * k + i + "");
                button.setBackgroundColor(Color.RED);
                button.setTextColor(Color.WHITE);
                LinearLayout.LayoutParams lpButton=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                lpButton.setMargins(10,10,10,10);
                lpButton.weight=1;
                rootHorizontal.addView(button,lpButton);//勿忘
            }
            lpHorizontal.weight=1;
            layout.addView(rootHorizontal,lpHorizontal);//勿忘
        }
    }
}
