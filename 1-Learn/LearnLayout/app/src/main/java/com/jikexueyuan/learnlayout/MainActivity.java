package com.jikexueyuan.learnlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
//用代码控制子对象
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout root;
    private Button btnClickme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);//下边用程序控制子对象
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        setContentView(root);
        for (int i = 0; i < 5; i++) {
            btnClickme = new Button(this);
            btnClickme.setText("remove me");
            btnClickme.setOnClickListener(this);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            root.addView(btnClickme,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);//将
// 第二个参数提取出来以便设置weight=1；
            lp.weight=1;
            root.addView(btnClickme,lp);
        }
        //添加一个按钮方法：
//        root.addView(btnClickme);
//        root.addView(btnClickme,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        先申明布局参数，根据参数添加子对象。
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        root.addView(btnClickme, lp);

    }

    @Override
    public void onClick(View v) {
        root.removeView(v);
    }
}
