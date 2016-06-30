package com.jikexueyuan.myapplicationfortest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

public class AnotherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        System.out.println("!!onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        System.out.println("!!onSaveInstanceState");

    }
    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("!!onStop");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("!!onRestoreInstanceState");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("!!onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("!!onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("!!onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("!!onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("!!onDestroy");
    }
}
