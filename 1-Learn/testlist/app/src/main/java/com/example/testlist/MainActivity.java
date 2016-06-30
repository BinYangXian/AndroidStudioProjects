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
//MainActivity��Ҫʵ���Զ���ӿ�
public class MainActivity extends Activity implements OnItemClickListener,//һ���˴�ʵ��Callback�ӿ��е�click������
                                                                  // ͬʱҲ��ʵ��OnClickListener�ӿ��е�click������
                                                                    //һ��˫���𣿣����ǣ�
		Callback {

	// ģ��listview�м��ص�����
	private static final String[] CONTENTS = { "����", "�Ϻ�", "����", "����", "����",
			"�Ͼ�", "�人", "��ɳ", "����" };
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
		mListView.setAdapter(new ContentAdapter(this, contentList, this));//��������3.��ʵ����Callback�ӿ�
		                                                                      // �����ı��ഫ�ݵ�ContentAdapter���С�

		mListView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ��ӦListView��item�ĵ���¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		Toast.makeText(this,"listView��item������ˣ�λ���ǡ�����>" + position,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * �ӿڷ�������ӦListView��ť����¼�
	 */
	@Override
	public void click(View v) {
		Toast.makeText(
				MainActivity.this,
				"listView��item�ڲ��İ�ť������ˣ�λ���ǣ�"+ (Integer) v.getTag() + ",�����ǣ�"
						+ contentList.get((Integer) v.getTag()),
				Toast.LENGTH_SHORT).show();
	}
}