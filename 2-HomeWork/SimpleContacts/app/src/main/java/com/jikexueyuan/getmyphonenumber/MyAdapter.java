package com.jikexueyuan.getmyphonenumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fangc on 2016/1/29.
 */
public class MyAdapter extends BaseAdapter { //为了将lists中数据显示到activity，可用adapter与ListView资源配合显示，系统的simpleCursorAdapter不能满足时,就自定义
    private List<PhoneInfo> lists;
    private LinearLayout layout;
    private Context context;       //因为我们很多的操作都依赖于Context，所以此处保留context实例的引用
                                    // 用来承接上下文语境。

    public MyAdapter(List<PhoneInfo> lists, Context context) {//初始化MyAdapter（我的适配器），传入存储类型为PhoneInfo的List集合、上下文语境对象
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lists.size();  //返回集合总数据有多少条
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }//返回当前列表项所关联的数据模型对象

    @Override
    public long getItemId(int position) {
        return position;                   //返回当前列表项的id，这里返回position，因为一般情况下id与position是相同的
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//此方法中，我们必须获取到当前要加载的
    // 地方（ListView的位置），同时把内容加载到每一item 视图中。
//        LayoutInflater inflater = LayoutInflater.from(context); //获取布局加载view的权限
//        layout = (LinearLayout) inflater.inflate(R.layout.cell, null);  //通过inflater（加载器）来加载布局资源文件,注意包的导入不要错了，否则找不到cell
//        TextView nametv = (TextView) layout.findViewById(R.id.name);
//        TextView numbertv = (TextView) layout.findViewById(R.id.number);
////        ImageView imageView= (ImageView) layout.findViewById(R.id.imageView);
//        nametv.setText(lists.get(position).getName());
//        numbertv.setText(lists.get(position).getNumber());
////        imageView.setImageResource(R.mipmap.ic_launcher);//由于通讯录中没有存图片，所以手动设置了个android studio的资源
//        return layout;//返回布局资源对象
        //下列是优化方法，当联系人十分多的时候，也不会卡或卡顿
        ViewHolder holder;
        if (convertView == null) {//如果等于空，说明并没有加载过此View既item UI对象(在适配器基类中，当有新的item UI对象需要显示时，
                              // 该getView方法循环执行而使得自定义代码，将数据逐条加载到逐个item UI对象中后在listView中显示，
                                    //当加载一定数量（此处为11条）的item UI对象后，消失的item0 UI对象便会被回收，
                                    // 而当第12条item11需要显示时，便会使用回收池中的item0 UI对象进行显示（显示成功时convertView不为空）。
            convertView = LayoutInflater.from(context).inflate(R.layout.cell, null);//加载View
            holder = new ViewHolder();            //实例化ViewHolder
            holder.nameTv = (TextView) convertView.findViewById(R.id.name); //建立关联
            holder.numberTv = (TextView) convertView.findViewById(R.id.number);
            convertView.setTag(holder);              //通过setTag将（加载过的两条TextView）标签进行存储，本次优化关键点！！！！
        } else {
            holder = (ViewHolder) convertView.getTag(); //如果convertView不为空，代表它是已经加载过的UI对象（view），
            // 是我们向下滑的动作，再次加载新的数据的原因，此刻不再加载View了，而是用ViewHolder中已储存好的View。
        }
        holder.nameTv.setText(lists.get(position).getName());  //cell中加载内容
        holder.numberTv.setText(lists.get(position).getNumber());
        return convertView;  //返回当前View
    }

    private static class ViewHolder {
        private TextView nameTv;
        private TextView numberTv;
        // TODO: 2016/4/21 可以添加ImageView等，方能体现自定义的优势，否则直接用系统的ArrayAdapter/simpleAdapter了！
    }
}
