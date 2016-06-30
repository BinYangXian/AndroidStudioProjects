package com.vip.flushdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends Activity implements
		OnRefreshListener2<ListView> {
	private PullToRefreshListView listView1;
	private List<String> lists = new ArrayList<String>();// 模拟数据
	private MyArrayAdapter myArrayAdapter;// 数据Adapter

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView1 = (PullToRefreshListView) findViewById(R.id.myPull_refresh_list);

		// 绑定Adapter
		myArrayAdapter = new MyArrayAdapter(this,
				android.R.layout.simple_list_item_1, getData());

		listView1.setAdapter(myArrayAdapter);

		listView1.setOnRefreshListener(this);// 记得绑定一下 监听类
	}

	// 模拟数据库查询
	int index = 1;

	private List<String> getData() {
		for (int i = 0; i < 10; i++) {
			lists.add("第" + index + "条");
			index++;
		}
		return lists;
	}

	// Adapter 你也懂的
	private class MyArrayAdapter extends ArrayAdapter<String> {
		public MyArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
		}

	}

	// 异步方式模拟请求数据
	private class GetDataTask extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			myArrayAdapter.notifyDataSetChanged();// 刷新Adapter
			listView1.onRefreshComplete();// 告诉它 我们已经在后台数据请求完毕
			Toast.makeText(MainActivity.this, "完成了", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			getData();// 继续刷数据
			try {
				Thread.sleep(2000);//暂停一下 只是为了效果更加明显
			} catch (Exception e) {
				e.printStackTrace();
			}
			publishProgress(0);//通知前台线程
			return 0;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		Toast.makeText(this, "下拉", Toast.LENGTH_SHORT).show();
		index = 0;// 刷新数据
		lists.clear();
		new GetDataTask().execute();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		Toast.makeText(this, "上拉", Toast.LENGTH_SHORT).show();
		new GetDataTask().execute();
	}

}
