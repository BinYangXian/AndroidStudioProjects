package com.jikexueyuan.learncontext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by fangc on 2016/1/4.
 */
public class Main2 extends Activity {
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        textView= (TextView) findViewById(R.id.textView);
        editText= (EditText) findViewById(R.id.editText);
        textView.setText("共享的数据是：" + getApp().getTextdate());
        findViewById(R.id.btnSaveDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App) getApplicationContext()).setTextdate(editText.getText().toString());//获取到这个信息，把它保存在全局ConText里边
                textView.setText("共享的数据是：" + editText.getText().toString());
            }
        });
    }
    public App getApp(){
        return (App) getApplicationContext();
    }
}
