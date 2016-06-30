package com.jikexueyuan.listviewexample.models;

/**
 * Created by fangc on 2016/2/21.
 */
public abstract class ExampleItem {

    private String label;

    public ExampleItem(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract void onAction();//！！点击首页列表项的抽象动作！！

    @Override
    public String toString() {//该重写方法使得adapter调用ExampleItem类型中的label进行适配！例子三
        return label;
    }//首页各列表项的名字
}
