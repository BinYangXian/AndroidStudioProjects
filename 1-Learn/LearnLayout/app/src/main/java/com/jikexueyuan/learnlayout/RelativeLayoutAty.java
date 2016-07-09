package com.jikexueyuan.learnlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RelativeLayoutAty extends AppCompatActivity {
private RelativeLayout root;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root=new RelativeLayout(this);
        setContentView(root);

        tv=new TextView(this);
        tv.setText("我最亲爱的");
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin=200;
        lp.topMargin=500;
        root.addView(tv,lp);
    }
}
