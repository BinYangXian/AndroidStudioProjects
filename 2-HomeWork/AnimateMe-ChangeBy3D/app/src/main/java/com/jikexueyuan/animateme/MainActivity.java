package com.jikexueyuan.animateme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
//        getSupportFragmentManager()可以支持老版本android 2 的视图动画；而
// getFragmentManager()支持属性动画，效果几乎与上述相同

        getFragmentManager().beginTransaction().add(R.id.container, new FragmentMain()).commit();
    }
//由于不是support 工具包中的fragment，所以需要在此处手动抛出后退栈区的fragment，实现单极后退动作
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();//把后退栈里边的最后一项给移除
        }else {
            super.onBackPressed();//退出程序
        }
    }
}
