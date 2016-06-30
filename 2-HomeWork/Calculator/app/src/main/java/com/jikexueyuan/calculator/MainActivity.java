package com.jikexueyuan.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private int flag = 0;//算法：运算符存储器，1代表乘法,2代表除法；当上一个算子A与下一个算子B是做乘除运算，那么当B输入
    // 完毕时（既下一个运算符输入时）计算出A*B的结果并用register寄存起来；
    // 加减法由点击事件“=”最终汇总计算，以实现四则混合运算。
    ArrayList<Double> register = new ArrayList<>(); //算子寄存器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnMinus).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnPlus).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnClean).setOnClickListener(this);
        findViewById(R.id.btnResult).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn0:
                if (!textView.getText().equals("0"))
                    textView.append("0");
                break;
            case R.id.btn1:
                textView.append("1");
                break;
            case R.id.btn2:
                textView.append("2");
                break;
            case R.id.btn3:
                textView.append("3");
                break;
            case R.id.btn4:
                textView.append("4");
                break;
            case R.id.btn5:
                textView.append("5");
                break;
            case R.id.btn6:
                textView.append("6");
                break;
            case R.id.btn7:
                textView.append("7");
                break;
            case R.id.btn8:
                textView.append("8");
                break;
            case R.id.btn9:
                textView.append("9");
                break;
            case R.id.btnMinus:
                judgeAndCalculate();
                textView.setText(""); //显示清空
                textView.append("-");//对下一个输入数字取负
                break;
            case R.id.btnMultiply:
                judgeAndCalculate();
                flag = 1;
                textView.setText("");
                break;
            case R.id.btnPlus:
                judgeAndCalculate();
                textView.setText("");
                break;
            case R.id.btnDivide:
                judgeAndCalculate();
                flag = 2;
                textView.setText("");
                break;
            case R.id.btnClean:
                register.clear();
                textView.setText("");
                flag = 0;
                break;
            case R.id.btnResult:
                judgeAndCalculate();
                double result = register.get(register.size() - 1);//result初值赋值为当前显示数（既是最后一个输入数字）
                double j = 0;  //j需要每次点击“=”时候被清0
                if (register.size() >= 2) {                   //当寄存器中至少有2个数据时候;
                    for (int i = 0; i < register.size() - 1; i++) {  //把他们加起来，
                        j = register.get(i);
                        result += j;
                    }
                }                         //当寄存器仅有一个数据时候，则无需动作
                register.clear();                      //清空寄存器，
                textView.setText(result + "");
                break;
        }
    }

    private void judgeAndCalculate() {
        if (flag == 1) {   //如果乘数标志有一个乘数(1代表乘法,2代表除法)，那么将之与当前数相乘，后存入register寄存器
            register.add(register.size() - 1, register.get(register.size() - 1) * Double.parseDouble(textView.getText().toString()));
            register.remove(register.size() - 1);
            flag = 0;//乘数标志清空
        } else if (flag == 2) {
            register.add(register.size() - 1, register.get(register.size() - 1) / Double.parseDouble(textView.getText().toString()));
            register.remove(register.size() - 1);
            flag = 0;//乘数标志清空
        } else {
            register.add(Double.parseDouble(textView.getText().toString()));
        }
    }
}
