package com.jikexueyuan.l02layoutchangeanim;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
//4 布局内容改变动画
public class MainActivity extends AppCompatActivity {

    private LinearLayout rootView;
    private View.OnClickListener btn_onclickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {//参数v代表被点击的按钮
            rootView.removeView(v);
        }
    };
    private  LayoutTransition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        rootView= (LinearLayout) findViewById(R.id.rootView);
//自定义layout的动画效果：定义一个旋转的属性动画，这里将动画对象置空，因为系统内部会将添加的view设置
// 为动画对象。然后调用setAnimator()方法将动画设置进LayoutTransition对象mTransitioner中。
        transition = new LayoutTransition();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(null, "rotationY", 0, 360);
        transition.setAnimator(LayoutTransition.APPEARING, animator1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch (item.getItemId()){
           case  R.id.action_settings:
               return true;
           case R.id.action_add:
               addButton();
               break;
       }

        return super.onOptionsItemSelected(item);
    }

    private void addButton() {
        Button btn=new Button(this);
        btn.setText("Remove me");
        rootView.addView(btn);

        btn.setOnClickListener(btn_onclickListener);

//设置上述onCreate方法中自定义LayoutTransition.APPEARING时的动画效果：
        rootView.setLayoutTransition(transition);
    }
}
