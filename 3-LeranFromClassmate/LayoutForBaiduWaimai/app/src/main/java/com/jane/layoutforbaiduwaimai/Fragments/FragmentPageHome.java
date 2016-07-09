package com.jane.layoutforbaiduwaimai.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jane.layoutforbaiduwaimai.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jane on 16/1/27.
 */
public class FragmentPageHome extends Fragment {
    SimpleAdapter mAdapterCategory,mAdapterList;
    GridView mLayoutCategory;
    ListView list;

    ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
    ArrayList<HashMap<String, Object>> mList = new ArrayList<HashMap<String, Object>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = (View) inflater.inflate(R.layout.fragment_home,null);


        list = (ListView) rootView.findViewById(android.R.id.list);
        View headerView = inflater.inflate(R.layout.layout_grid_home, null);
        View textviewView = inflater.inflate(R.layout.layout_textview_home, null);

        mLayoutCategory = (GridView) headerView.findViewById(R.id.layout_category);

        list.addHeaderView(headerView);
        list.addHeaderView(textviewView);

        setCategory();
        setDian();

        return rootView;
    }

    public void setCategory(){
        int[] imageview = {R.drawable.eat,R.drawable.fruit,R.drawable.medicine,R.drawable.newdian,R.drawable.tuhao,R.drawable.tea,R.drawable.buy,R.drawable.deliver};
        String[] textview = {"餐饮","水果","药店","新店","土豪","下午茶","超市购","快送"};

        for (int i = 0; i < imageview.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("imageview", imageview[i]);
            map.put("textview", textview[i]);
            meumList.add(map);
        }

        mAdapterCategory = new SimpleAdapter(getActivity().getApplicationContext(), meumList, R.layout.layout_category, new String[]{"imageview", "textview"}, new int[]{R.id.image_view, R.id.text});
        Log.i("MainActivity", "" + mAdapterCategory.getCount());

        mLayoutCategory.setAdapter(mAdapterCategory);
    }

    public void setDian(){
        int[] images = {R.drawable.shopone,R.drawable.shoptwo,R.drawable.shopthree};
        String[] names = {"麻辣烫","烧酒店","花店"};
        int[] xing = {R.drawable.businesslistings_list_icon_star_full,R.drawable.businesslistings_list_icon_star_half,R.drawable.businesslistings_list_icon_star_no};
        String[] qisong = {"起送¥20｜配送¥5｜平均45分钟","起送¥20｜配送¥5｜平均30分钟","起送¥20｜配送¥0｜平均45分钟"};
        int juan = R.drawable.waimai_shoplist_item_icon_jian;
        int jian = R.drawable.waimai_shoplist_item_icon_juan;
        int mian = R.drawable.waimai_shoplist_item_icon_mian;

        for (int i = 0; i < images.length; i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("image",images[i]);
            map.put("name",names[i]);
            map.put("xing",xing[i]);
            map.put("qisong",qisong[i]);
            map.put("juan",juan);
            map.put("jian",jian);
            map.put("mian",mian);

            mList.add(map);
        }

        mAdapterList = new SimpleAdapter(
                getActivity().getApplicationContext(),
                mList,
                R.layout.layout_list_home,
                new String[]{"image", "name","xing","qisong","jian","juan","mian"},
                new int[]{R.id.dian_image, R.id.dian_name,R.id.xing,R.id.qisong,R.id.jian,R.id.juan,R.id.mian});

        list.setAdapter(mAdapterList);
    }

    public void setLayout(){

    }

    //防止fragment切换后，布局内的list、GridView中数据依旧保存，导致数量不断递增
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        meumList = new ArrayList<>();
        mList = new ArrayList<>();
    }
}
