package com.jikexueyuan.calllater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//功能模拟：我通过Callater设置定时开机
public class Me extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("button clicked");
//                Callater.callater(new Callater.ICallBack() {    //通过callater方法使用Callater
//                    @Override
//                    public void execute() {
//                        System.out.println("excute执行了");
//                    }
//                },2000);
//            }
//        });
                new Callater(new Callater.ICallBack() {   //直接使用Callater（此处需要一个
                // ICallBack的实例或叫对象引用，故new了匿名的Callater类中ICallBack类的实例）
                    @Override
                    public void execute() {
                        System.out.println("定时开机成功");
                    }
                },3000);
              //  new Callater(null,3000); //当Callater的第一个传入参数为null,（类似于按钮没有注册实例（实际）监听者，或者说为实例监听者为null，那么点击按钮后不会有任何动作；又类似电话的通讯线接口，当拔掉既传值为null，即使有来电也不会响）,这样用ICallBack接口使得耦合性就很低
            }
        });
    }
}