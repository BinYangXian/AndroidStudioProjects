package com.homer.phone;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class phoneRead extends Activity {
	  
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		showListView();
	}
	
	private void showListView(){
		ListView listView = new ListView(this);
		
		ArrayList<HashMap<String, String>> list = getPeopleInPhone2();
		SimpleAdapter adapter = new SimpleAdapter(
									this, 
									list, 
									android.R.layout.simple_list_item_2, 
									new String[] {"peopleName", "phoneNum"}, 
									new int[]{android.R.id.text1, android.R.id.text2}
								);
		listView.setAdapter(adapter);
		
		setContentView(listView);
	}
	
	private ArrayList<HashMap<String, String>> getPeopleInPhone2(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);		// 获取手机联系人
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			
			int indexPeopleName = cursor.getColumnIndex(Phone.DISPLAY_NAME); 	// people name
			int indexPhoneNum = cursor.getColumnIndex(Phone.NUMBER);	 		// phone number

			String strPeopleName = cursor.getString(indexPeopleName);
			String strPhoneNum = cursor.getString(indexPhoneNum);

			map.put("peopleName", strPeopleName);
			map.put("phoneNum", strPhoneNum);
			list.add(map);
		}
        if(!cursor.isClosed()){
        	cursor.close();
        	cursor = null;
        }
		
        return list;
	}
}



/*


	private void showTextView(){
		TextView tv = new TextView(this);
		
		tv.setText(getPeopleInPhone());
        setContentView(tv);
	}
	
	
	private String getPeopleInPhone(){
		StringBuilder strBuilder = new StringBuilder();
		
		Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			int indexPeopleName = cursor.getColumnIndex(Phone.DISPLAY_NAME); 	// people name
			int indexPhoneNum = cursor.getColumnIndex(Phone.NUMBER); 			// phone number

			String strPeopleName = cursor.getString(indexPeopleName);
			String strPhoneNum = cursor.getString(indexPhoneNum);

			strBuilder.append("[ " + strPeopleName);
			strBuilder.append(" : " + strPhoneNum + " ]\n\n");
		}
        if(!cursor.isClosed()){
        	cursor.close();
        	cursor = null;
        }
		
        return strBuilder.toString();
	}
*/


