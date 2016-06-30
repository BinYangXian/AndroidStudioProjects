package com.example.testlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testlist.ContentAdapter.Callback;

import java.util.ArrayList;
import java.util.List;
//MainActivity需要实现自定义接口
public class MainActivity extends Activity implements OnItemClickListener,//一、此处实现Callback接口中的click方法，
                                                                  // 同时也是实现OnClickListener接口中的click方法。
                                                                    //一语双关吗？！不是！
		Callback {

	// 模拟listview中加载的数据
	private static final String[] CONTENTS = { "北京", "上海", "广州", "深圳", "苏州",
			"南京", "武汉", "长沙", "杭州" };
	private List<String> contentList;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		mListView = (ListView) findViewById(R.id.listview);
		contentList = new ArrayList<String>();
		for (int i = 0; i < CONTENTS.length; i++) {
			contentList.add(CONTENTS[i]);
		}
		//
		mListView.setAdapter(new ContentAdapter(this, contentList, this));//二、参数3.将实现了Callback接口
		                                                                      // 方法的本类传递到ContentAdapter类中。

		mListView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 响应ListView中item的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		Toast.makeText(this,"listView的item被点击了，位置是———>" + position,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 接口方法，响应ListView按钮点击事件
	 */
	@Override
	public void click(View v) {
		Toast.makeText(
				MainActivity.this,
				"listView的item内部的按钮被点击了，位置是："+ (Integer) v.getTag() + ",内容是："
						+ contentList.get((Integer) v.getTag()),
				Toast.LENGTH_SHORT).show();
	}
}