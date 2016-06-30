package com.jane.layoutforbaiduwaimai.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jane.layoutforbaiduwaimai.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jane on 16/1/27.
 */
public class FragmentPageMe extends Fragment {

    ListView mListviewLayout;
    SimpleAdapter mAdapterMe;

    ArrayList<HashMap<String, Object>> listMe = new ArrayList<HashMap<String, Object>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = (View) inflater.inflate(R.layout.fragment_me,null);
        mListviewLayout = (ListView) rootView.findViewById(android.R.id.list);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setList();
    }

    private void setList() {
        int[] images = {R.drawable.mypage_list_icon_location,
                R.drawable.mypage_list_icon_daijinjuan,
                R.drawable.mypage_list_icon_refund,
                R.drawable.my_messages,
                R.drawable.mypage_list_icon_star,
                R.drawable.mypage_list_icon_comment,
                R.drawable.my_balance_icon,
                R.drawable.icon_nuomi};

        String[] names = {"我的送餐地址","我的代金券","我的退款","我的消息","我的收藏","我的评论","百度钱包","百度糯米"};

        int icon = R.drawable.wallet_base_indicator_arrow;

        for (int i = 0; i < images.length; i++) {
            HashMap<String,Object> map = new HashMap<>();
            map.put("image",images[i]);
            map.put("name",names[i]);
            map.put("icon",icon);

            listMe.add(map);
        }

        mAdapterMe = new SimpleAdapter(
                getActivity().getApplicationContext(),
                listMe,
                R.layout.layout_list_me,
                new String[]{"image","name","icon"},
                new int[]{R.id.image_me,R.id.textview_me,R.id.button_me});

        mListviewLayout.setAdapter(mAdapterMe);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        listMe = new ArrayList<>();//防止fragment切换后，布局内的list、GridView中数据依旧保存，导致数量不断递增
    }
}
