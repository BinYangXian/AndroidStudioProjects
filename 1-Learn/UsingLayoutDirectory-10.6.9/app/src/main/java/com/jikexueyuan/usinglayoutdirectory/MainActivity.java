package com.jikexueyuan.usinglayoutdirectory;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private People people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("onCreate");

        if (getLastCustomNonConfigurationInstance()!=null){
            // TODO: 2016/3/23  横竖屏切换
            people= (People) getLastCustomNonConfigurationInstance();//获得初始化保存的数据
            System.out.println("last:"+people.getName());
            System.out.println("last:"+people.getAge());
        }else {
            initDate();
        }
    }

    private void initDate() {
        people=People.getPeopleInstance();
        people.setName("张三");
        people.setAge(29);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @Override
    public void onContentChanged() {//当屏幕变化时调用此
        super.onContentChanged();
        System.out.println("onContentChanged");
        int myOrientation = getResources().getConfiguration().orientation;//获得屏幕方向
        if (myOrientation== Configuration.ORIENTATION_PORTRAIT){
            System.out.println("横屏");
        }else if (myOrientation== Configuration.ORIENTATION_LANDSCAPE){
            System.out.println("竖屏");
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {//此法在onStop()与onDestroy()之间执行，可以用
    // 此法保持数据，所以该法返回值为Object类型(既能保存所有类型的数据)
        System.out.println("onRetainCustomNonConfigurationInstance");

        people=People.getPeopleInstance();
//        return super.onRetainCustomNonConfigurationInstance();
        return people;
    }
}
