package com.jikexueyuan.contentreaderacrossapp;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout main;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        main=new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        setContentView(main);

        button=new Button(this);
        main.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContent();
            }
        });

    }

    private void addContent() {
        ContentValues values = new ContentValues();
        values.put("name", "xiaoli");
        ContentValues values2 = new ContentValues();
        values2.put("name", "yingying");
        getContentResolver().insert(MyProvider.URI, values);
        getContentResolver().insert(MyProvider.URI, values2);
    }
}
