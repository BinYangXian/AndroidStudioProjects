package com.jikexueyuan.baiduwaimailayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jikexueyuan.baiduwaimailayout.R;
import com.jikexueyuan.baiduwaimailayout.models.ShopMessage;

import java.util.ArrayList;

/**
 * Created by fangc on 2016/2/29.
 */

public class HomePageAdapter extends BaseAdapter {

    public HomePageAdapter(ArrayList<ShopMessage> shopMessages, Context context) {
        this.shopMessages = shopMessages;
        this.context = context;
    }

    @Override
    public ShopMessage getItem(int position) {
        return shopMessages.get(position);
    }//返回当前列表项所关联的数据模型对象

    @Override
    public long getItemId(int position) {
        return position;                   //返回当前列表项的id，这里返回position，因为一般情况下id与position是相同的
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListCellViewHolder viewHolder = null;
        if (convertView == null) {//列表优化1：列表项UI对象的重用是对内存的优化。
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shops_lv_cell, null);
            viewHolder = new CustomListCellViewHolder();
            viewHolder.shop_distance = (TextView) convertView.findViewById(R.id.shop_distance);
            viewHolder.shops_name = (TextView) convertView.findViewById(R.id.shops_name);
            viewHolder.shop_sold_number = (TextView) convertView.findViewById(R.id.shop_sold_number);
            viewHolder.shop_extra_messages = (TextView) convertView.findViewById(R.id.shop_extra_messages);
            viewHolder.shops_image = (ImageView) convertView.findViewById(R.id.shops_image);
            viewHolder.star_image[0] = (ImageView) convertView.findViewById(R.id.evaluated_star1);
            viewHolder.star_image[1] = (ImageView) convertView.findViewById(R.id.evaluated_star2);
            viewHolder.star_image[2] = (ImageView) convertView.findViewById(R.id.evaluated_star3);
            viewHolder.star_image[3] = (ImageView) convertView.findViewById(R.id.evaluated_star4);
            viewHolder.star_image[4] = (ImageView) convertView.findViewById(R.id.evaluated_star5);
            viewHolder.totalHalfStar = getItem(position).getHalfStar();
            convertView.setTag(viewHolder);//ViewHolder的使用

        } else {
            viewHolder = (CustomListCellViewHolder) convertView.getTag();
        }

        ShopMessage item = getItem(position);

        viewHolder.shop_distance.setText(item.getDistance());
        viewHolder.shop_extra_messages.setText(item.getExtraMessages());
        viewHolder.shop_sold_number.setText(item.getSoldNumber());
        viewHolder.shops_image.setImageResource(item.getShopImageId());
        viewHolder.shops_name.setText(item.getName());
        int i;
        for (i = 0; i < viewHolder.totalHalfStar / 2; i++) {
            viewHolder.star_image[i].setImageResource(R.drawable.businesslistings_list_icon_star_full);
        }
        if (viewHolder.totalHalfStar % 2 != 0) {
            viewHolder.star_image[i].setImageResource(R.drawable.businesslistings_list_icon_star_half);
        }

        return convertView;
    }


    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return shopMessages.size();  //返回集合总数据有多少条
    }

    private ArrayList<ShopMessage> shopMessages;
    private Context context;       //因为我们很多的操作都依赖于Context，所以此处保留context实例的引用

    // 用来承接上下文语境。
    private static class CustomListCellViewHolder {//需要是static，这样的成员内部类是属于该类的，使用时不会重复分配内存
        private TextView shops_name, shop_sold_number, shop_extra_messages, shop_distance;
        private ImageView shops_image;
        private int totalHalfStar;
        private ImageView star_image[] = new ImageView[5];
    }

}


