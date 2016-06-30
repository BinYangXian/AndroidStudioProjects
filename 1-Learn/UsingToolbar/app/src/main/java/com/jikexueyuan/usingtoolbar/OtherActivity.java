package com.jikexueyuan.usingtoolbar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by fangc on 2016/2/25.
 */
public class OtherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.other_toolbar);
        setSupportActionBar(toolbar);//自定义toolbar，且在不同的android版本中几乎没有变化

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
