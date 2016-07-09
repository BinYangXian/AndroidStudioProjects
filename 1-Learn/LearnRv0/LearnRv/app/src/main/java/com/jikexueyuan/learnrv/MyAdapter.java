package com.jikexueyuan.learnrv;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fangc on 2016/2/22.
 */
class MyAdapter extends RecyclerView.Adapter {
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;// 例子1
        public MyViewHolder(TextView itemView) {//由于上下文想绑定的是tv，所以更改构造函数传入参数View为TextView，
            // 避免了对tv进行cast to View。
            super(itemView);
            tv = itemView;
        }
        public TextView getTv() {//为外部提供公开tvContent的方法
            return tv;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new TextView(parent.getContext()));//此MyViewHolder为自定义的；例子1
        //接下来创建View的方法，根据资源来创建：
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//此处可以对tv赋值；参数1.上个方法返回的
        // 自定义类型holder对象变量、参数2.索引，当前初始化的数据所的位置索引。例子1
        MyViewHolder vh = (MyViewHolder) holder;
        vh.getTv().setText(data[position]);//数据显示，例子1
    }

    @Override
    public int getItemCount() {
        return data.length;//返回RecyclerView子对象的数量
    }

    private String[] data = new String[]{"hello", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart", "sweet", "heart"};//例子1
}
