package com.jikexueyuan.addcardsbycode;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

//用代码控制子对象
public class MainActivity extends AppCompatActivity {

    private LinearLayout root, horizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        setContentView(root);//此话在上两句话之后

        for (int i = 0; i < 5; i++) {
            horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams horizontalLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);//
            for (int j = 0; j < 4; j++) {
                Button button = new Button(this);
                button.setBackgroundColor(Color.RED);
                button.setText(i * 4 + j + 1 + "");
                button.setTextColor(Color.WHITE);
                LinearLayout.LayoutParams buttonLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                buttonLp.setMargins(10, 10, 10, 10);
                buttonLp.weight = 1;
                horizontalLayout.addView(button,buttonLp);
            }
            horizontalLp.weight = 1;
            root.addView(horizontalLayout,horizontalLp);
        }
    }

}