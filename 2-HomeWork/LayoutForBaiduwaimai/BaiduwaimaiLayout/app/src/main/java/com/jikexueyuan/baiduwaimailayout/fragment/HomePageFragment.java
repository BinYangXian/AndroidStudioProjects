package com.jikexueyuan.baiduwaimailayout.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jikexueyuan.baiduwaimailayout.R;
import com.jikexueyuan.baiduwaimailayout.adapter.HomePageAdapter;
import com.jikexueyuan.baiduwaimailayout.models.ShopMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomePageFragment extends Fragment {
    private ArrayList<ShopMessage> shopMessages= new ArrayList<>();;
    private List<HashMap<String, Object>> gridViewList= new ArrayList<>();; //布局头部的表格布局所需的元素集合
    private SimpleAdapter adapter;
    private GridView homeHeaderGridview;

    public HomePageFragment() {
        //主页列表数据添加
        shopMessages.add(new ShopMessage("必胜客欢乐餐厅（上...", "990m", "起卖￥20|配送￥5|平均45分钟", "已售566份", R.drawable.shopone, 9));
        shopMessages.add(new ShopMessage("汉拿山（上地华联店...", "1.0km", "起卖￥20|配送￥5|平均45分钟", "已售910份", R.drawable.shoptwo, 8));
        shopMessages.add(new ShopMessage("正一味（上地华联店...", "1.5km", "起卖￥20|配送￥5|平均45分钟", "已售766份", R.drawable.shopthree, 7));
        //主页布局头部的表格布局数据添加
        String[] textView = {"餐饮", "水果", "药店", "新店", "土豪", "下午茶", "超市购", "快送"};
        int[] imageView = {R.drawable.eat, R.drawable.fruit, R.drawable.medicine, R.drawable.newdian, R.drawable.tuhao, R.drawable.tea, R.drawable.buy, R.drawable.deliver};
        for (int i = 0; i < imageView.length; i++) {
            HashMap<String, Object> gridViewMap = new HashMap<>();
            gridViewMap.put("textView", textView[i]);
            gridViewMap.put("imageView", imageView[i]);
            gridViewList.add(gridViewMap);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        ListView shops_lv = (ListView) rootView.findViewById(R.id.shops_lv);
//1、用home_header_gridview作为布局资源进行加载创建一个View，2、以此headerView为载体，建立GridView与资源id的关联；
// 3、加载它作为listView的头部
        View headerView = inflater.inflate(R.layout.home_header_gridview, null);
        homeHeaderGridview = (GridView) headerView.findViewById(R.id.homeHeaderGridview);
        shops_lv.addHeaderView(headerView);
        //数据适配：1、表格头部布局的单条数据适配方法 2、表格头部布局整体数据适配;3、列表数据的适配
        adapter = new SimpleAdapter(getContext(), gridViewList, R.layout.home_header_gridview_cell, new String[]{"textView", "imageView"}, new int[]{R.id.homeTv, R.id.homeImageBtn});
        homeHeaderGridview.setAdapter(adapter);
        shops_lv.setAdapter(new HomePageAdapter(shopMessages, getContext()));

        return rootView;
    }

}

