package com.jikexueyuan.mulchoose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MulChoose extends AppCompatActivity {
    private CheckBox[] checkBox = new CheckBox[9];
    private Button button;
    private String right = "010011001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mul_choose);
        initView();
        initEvent();
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                for (int i = 0; i < 9; i++) {           //此处也无法用for-each循环呢？
                    if (checkBox[i].isChecked()) {
                        str = str + "1";
                    } else {
                        str = str + "0";
                    }
                }
                if (str.equals(right)) {
                    Toast.makeText(MulChoose.this, "选择正确", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MulChoose.this, "选择错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //初始化控件
    private void initView() {
        button = (Button) findViewById(R.id.button);
        checkBox[0] = (CheckBox) findViewById(R.id.checkBox1);//此处无法用for循环呢？
        checkBox[1] = (CheckBox) findViewById(R.id.checkBox2);
        checkBox[2] = (CheckBox) findViewById(R.id.checkBox4);
        checkBox[3] = (CheckBox) findViewById(R.id.checkBox3);
        checkBox[4] = (CheckBox) findViewById(R.id.checkBox5);
        checkBox[5] = (CheckBox) findViewById(R.id.checkBox6);
        checkBox[6] = (CheckBox) findViewById(R.id.checkBox7);
        checkBox[7] = (CheckBox) findViewById(R.id.checkBox8);
        checkBox[8] = (CheckBox) findViewById(R.id.checkBox9);
    }


}
