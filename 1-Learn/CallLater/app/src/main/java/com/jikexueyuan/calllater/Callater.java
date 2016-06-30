package com.jikexueyuan.calllater;

/**
 * Created by fangc on 2016/1/19.
 */                           //由于java语言没有指针，它无法让回调指向某个方法，所以只能把回调定位到类
public class Callater {       //外界通过new Callater来使用该类,但是也可以创建下边static方法来方便外部的使用

    public static void callater(ICallBack callback, long delay) {//当该方法被调用时，需要的是两实际参数
        new Callater(callback, delay);
    }

    private final ICallBack callback;  //定义接口类型的变量

    public Callater(final ICallBack callback, final long delay) {  //构造方法需要接收接口类型的参数，
    // 与long型的参数（此处为形式参数，实际创建本类的实例调用该方法时，显然需要实参）
        this.callback = callback;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (callback!=null){  //当callback没有引用对象时候,（类似于按钮没有注册实例（实际）监听者，
                // 或者说为实例监听者为null，那么点击按钮后不会有任何动作）
                    callback.execute();
                }
            }
        }.start();

    }
    interface ICallBack {   //类中的成员可以有接口！！当程序延时后，通过实现了接口匿名类调用execute方法
    // 得以确定实例化中的代码。
        void execute();
    }
}
