package com.jikexueyuan.pulltoloadmore;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fangc on 2016/5/18.
 */

public class MainActivity extends Fragment implements AbsListView.OnScrollListener {

    private static final String TAG = "MainActivity";

    private ListView listView;
    private View moreView; //加载更多页面

    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, String>> listData;

    private int lastItem;
    private int count;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView);
        moreView = View.inflate(getActivity(), R.layout.load_more, null);
        listData = new ArrayList<HashMap<String, String>>();

        prepareData(); //准备数据
        count = listData.size();

        adapter = new SimpleAdapter(getActivity(), listData, R.layout.item,
                new String[]{"itemText"}, new int[]{R.id.itemText});

        listView.addFooterView(moreView); //添加底部view，一定要在setAdapter之前添加，否则会报错。

        pullToRefresh(rootView);

        listView.setAdapter(adapter); //设置adapter
        listView.setOnScrollListener(this); //设置listview的滚动事件
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void prepareData() { //准备数据
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemText", "测试数据" + i);
            listData.add(map);
        }

    }

    private void loadMoreData() { //加载更多数据
        count = adapter.getCount();
        for (int i = count; i < count + 5; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemText", "测试数据" + i);
            listData.add(map);
        }
        count = listData.size();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

//        Log.i(TAG, "firstVisibleItem=" + firstVisibleItem + "\nvisibleItemCount=" +
//                visibleItemCount + "\ntotalItemCount" + totalItemCount);

        lastItem = firstVisibleItem + visibleItemCount - 1; //减1是因为上面加了个addFooterView

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.i(TAG, "scrollState=" + scrollState);
        //下拉到空闲时，且最后一个item的数等于数据的总数时，进行更新
        if (lastItem == count && scrollState == SCROLL_STATE_IDLE) {
            Log.i(TAG, "拉到最底部");
            moreView.setVisibility(View.VISIBLE);

            mHandler.sendEmptyMessage(0);

        }

    }

    //声明Handler
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    loadMoreData(); //加载更多数据，这里可以使用异步加载
                    adapter.notifyDataSetChanged();
                    moreView.setVisibility(View.GONE);

                    if (count > 30) {
                        Toast.makeText(getActivity(), "木有更多数据！", Toast.LENGTH_SHORT).show();
                        listView.removeFooterView(moreView); //移除底部视图
                    }
                    Log.i(TAG, "加载更多数据");
                    break;
                case 1:

                    break;
                default:
                    break;
            }
        }

    };

    public void pullToRefresh(View rootView) {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeLayout);

//        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
//        R.color.swipe_color_2,
//        R.color.swipe_color_3,
//        R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        swipeRefreshLayout.setPadding(20, 20, 20, 20);
        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
        swipeRefreshLayout.setDistanceToTriggerSync(50);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Note", "下拉刷新");

            }
        });

    }
}