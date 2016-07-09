package com.jikexueyuan.learnrv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fangc on 2016/2/22.
 */
//本课讲解如何创建列表项资源以及如何解析列表项资源并应用在列表中！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
class MyAdapter extends RecyclerView.Adapter {
    class MyViewHolder extends RecyclerView.ViewHolder {
        private View root;
        private TextView tvTitle,tvContent;
        public MyViewHolder(View root) {
            super(root);
            tvTitle= (TextView) root.findViewById(R.id.tvTitle);                                        //2、解析列表项资源：此传进来的参数root 是onCreateViewHolder方法返回的
            // list_cell（与例子1同为View类型对象），可以据此获取到TextView对象：tvTitle,tvContent。
            tvContent= (TextView) root.findViewById(R.id.tvContent);
        }
//        private TextView tv; 例子1
//        public MyViewHolder(TextView itemView) {//由于上下文想绑定的是tv，所以更改构造函数传入参数View为
//        TextView（为View类型对象），避免了对tv进行cast to View。
//            super(itemView);
//            tv = itemView;}
        public TextView getTvContent() {//为外部提供公开tvContent的方法
            return tvContent;
        }

        public TextView getTvTitle() {//为外部提供公开getTvTitle的方法
            return tvTitle;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MyViewHolder(new TextView(parent.getContext()));//此MyViewHolder为自定义的；需要的参数是TextView类型，例子1
        //接下来创建View的方法，根据资源来创建更方便：

        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell,null));//1、返回的是自定义列表项资源。
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//此处可以对tv赋值；参数1.上个方法返回的
        // 自定义类型holder对象变量、参数2.索引，当前初始化的数据所的位置索引。
        MyViewHolder vh = (MyViewHolder) holder;
        //数据显示：
        CellData cd=data[position];
        vh.getTvTitle().setText(cd.title);                                                                  //3、将上述解析的列表项资源并应用在列表中
        vh.getTvContent().setText(cd.content);
    }

    @Override
    public int getItemCount() {
        return data.length;//返回RecyclerView子对象的数量
    }
    private CellData[] data=new CellData[]{new CellData("莹莹","温柔可人"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙"),new CellData("颜颜","知书达理，美若天仙")};
}
