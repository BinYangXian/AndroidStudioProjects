package com.example.testlist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testlist.ContentAdapter.MyClickListener;
public class MainActivity extends Activity  {

	 private static final String[] CONTENTS = { "����", "�Ϻ�", "����", "����", "����",
		              "�Ͼ�", "�人", "��ɳ", "����" };
		      private List<String> contentList;
		      private ListView mListView;
		      ContentAdapter adapter;
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
		          //ʵ����ContentAdapter�࣬������ʵ����
		           adapter=new ContentAdapter(this, contentList, mListener);
		          mListView.setAdapter(adapter);
		          
		     }
		
		      @Override
		      public boolean onCreateOptionsMenu(Menu menu) {
		         getMenuInflater().inflate(R.menu.main, menu);
		          return true;
		      }
		  
		    
		 
		     /**
		     * ʵ���࣬��Ӧ��ť����¼�
		      */
		      private MyClickListener mListener = new MyClickListener() {
		          @Override
		        public void myOnClick(int position, View v ) {
		             Toast.makeText(
		                     MainActivity.this,
		                      "listview���ڲ��İ�ť������ˣ���λ����-->" + position + ",������-->"
		                              + contentList.get(position), Toast.LENGTH_SHORT)
		                      .show();
		             
		             TextView tv= (TextView)v;
		          
		             contentList.set(position, "abc");
		             adapter.notifyDataSetChanged();
		             
		          }
		      };

}
