package com.jikexueyuan.changefragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
//        getSupportFragmentManager()仅支持老版本android 2 的视图动画；而
// getFragmentManager()支持属性动画，所以需要android 4.0及以上。

        getFragmentManager().beginTransaction().add(R.id.container, new FragmentMain()).commit();
    }
//由于getFragmentManager方法得到的不是support v7工具包中的fragment，所以需要在此处手动抛出后退栈区的fragment，实现单极后退动作
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();//把后退栈里边的最后一项给移除
        }else {
            super.onBackPressed();//退出程序
        }
    }
}
