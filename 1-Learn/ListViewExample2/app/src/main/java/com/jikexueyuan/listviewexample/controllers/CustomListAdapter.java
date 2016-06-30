package com.jikexueyuan.listviewexample.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jikexueyuan.listviewexample.R;
import com.jikexueyuan.listviewexample.models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fangc on 2016/2/21.
 */
public class CustomListAdapter extends BaseAdapter {

    public CustomListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }//返回当前列表项所关联的数据模型对象

    @Override
    public long getItemId(int position) {
        return position;                   //返回当前列表项的id，这里返回position，因为一般情况下id与position是相同的
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//参数：1.当前要获取的列表项的位置、
    // 2.如果等于空，说明并没有加载过此View既item UI对象(在适配器基类中，当有新的item UI对象需要显示时，
        // 该getView方法循环执行而使得自定义代码，将数据逐条加载到逐个item UI对象中后在listView中显示，
        //当加载一定数量（此处为11条）的item UI对象后，消失的item0 UI对象便会被回收，
        // 而当第12条item11需要显示时，便会使用回收池中的item0 UI对象（既返回convertView）进行显示（显示成功时convertView不为空）。
        if (convertView==null){//列表优化1：列表项UI对象的重用是对内存的优化。(当回收池中没有该convertView对象 才进行加载)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_cell,null);
            // 参数2.一般情况为null，因为它在初始化的时候不需要指定父级容器，系统里边会自动的把它添加到指定的
            // 父级容器里去。
            //列表优化2:CustomListCellViewHolder的使用是对运算速度的优化{原理是，将【CustomListCellViewHolder中类成员为
            // TextView类型的】tvTitle与tvDesc的变量赋值为查找到目标id（R.id.tvTitle与R.id.tvDesc）并建立了关联的TextView对象
            // 使用setTag方法进行储存，使用getTag方法来提取使用，可避免重复查找关联动作。}
            convertView.setTag(new CustomListCellViewHolder((TextView)convertView.findViewById(R.id.tvTitle),(TextView)convertView.findViewById(R.id.tvDesc)));//ViewHolder的使用
        }


        Student item=getItem(position);
        CustomListCellViewHolder viewHolder= (CustomListCellViewHolder) convertView.getTag();

        viewHolder.getTvTitle().setText(item.getName());
        viewHolder.getTvDesc().setText("年龄:" + item.getAge());
        return convertView;
    }


    public Context getContext() {
        return context;
    }
    @Override
    public int getCount() {
        return students.size();  //返回集合总数据有多少条
    }
    public void add(Student s){
        students.add(s);
    }
    private List<Student> students=new ArrayList<>();
    private Context context;       //因为我们很多的操作都依赖于Context，所以此处保留context实例的引用
    // 用来承接上下文语境。
}
