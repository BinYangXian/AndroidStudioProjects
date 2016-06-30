package com.jikexueyuan.thinknumbergame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private int count;
    private Button btn;
    private ThinkNumber thinkNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();//控件初始化
        initDate();//数据初始化
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("I've thinked a number between 0 and 100,you guess it：");
                btn.setText("Guess it now");
                initEvent();//猜数事件
            }
        });
    }

    private void initEvent() {
        if (!(editText.getText().toString()).equals("")) {
            int guessNumber = Integer.valueOf(editText.getText().toString());
            editText.setText("");
            if (guessNumber == (thinkNumber.getRandomNumber())) {
                textView.setText(String.format("就是%d,恭喜你猜对了，一共猜了%d次。", guessNumber, count));
                btn.setText("再玩一次");
                count = 0;
                thinkNumber = new ThinkNumber();
            } else {
                if (guessNumber > (thinkNumber.getRandomNumber())) {
                    textView.setText(String.format("猜大了，请继续，已经猜了%d次了哦。", ++count));
                } else {
                    textView.setText(String.format("猜小了，请继续，已经猜了%d次了哦。", ++count));
                }
            }
        }
    }

    private void initDate() {
        btn = (Button) findViewById(R.id.button);
        thinkNumber = new ThinkNumber();
        count = 0;
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
    }
}