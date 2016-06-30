package com.cking.smsinterception;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Contacts;

public class LocationDataSaveService extends Service{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//deleteFile ("locsms_file");
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		String oldcon = intent.getStringExtra("message");
		String phone=intent.getStringExtra("srcnum");
		String time=intent.getStringExtra("datatime");
		
		//通过号码找到姓名
		String name = null;
		String[] projection = new String[] { Contacts.Phones.DISPLAY_NAME,Contacts.Phones.NUMBER }; 	    
		Uri contactUri = Uri.withAppendedPath(Contacts.Phones.CONTENT_FILTER_URL, Uri.encode(phone)); 		   
		Cursor c = getContentResolver().query(contactUri, projection, null,null, null); 
		if (c.moveToFirst()){ 		        
			name = c.getString(c.getColumnIndex(Contacts.Phones.DISPLAY_NAME)); 
		}
				
		String con = oldcon + "," + phone + "," + name +"," + time;
		String filepath="/data/data/com.cking.smsinterception/files/";
		String FILENAME = "loc_sms.txt";
		String content=con+"\n";
		final int NUM=100;
		
/*××××××××××××××××××××计算文件的数据是否有100条×××××××××××××××××××××××××××××××××××××××××*/
		int count = 0;
		InputStream input;
		try {
			input = openFileInput(FILENAME);
			BufferedReader br = new BufferedReader(new InputStreamReader(input));

			String value = br.readLine();
			if(value != null)
			{

					while(value !=null){
					  count++;
					  value = br.readLine();
					}
			
					br.close();
					input.close();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
/*××××××××××××××××××××插入新的数据×××××××××××××××××××××××××××××××××××××××××*/
						
	if(count<NUM)					//如果还没100条，直接插入
		{
				FileOutputStream os01;
				try {
					os01 = openFileOutput(FILENAME, Context.MODE_WORLD_READABLE+Context.MODE_APPEND+Context.MODE_WORLD_WRITEABLE);
					os01.write(content.getBytes());
					os01.close();
					System.out.println("write done!");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
	else							//如果大于等于100条，先刷新，在插入
		{
					List<String> ls=new ArrayList<String> ();
					//把文件里的数据逐行的读入到LIST中
					try{      		
						
			        	System.out.println("begin read data from file!");
						FileReader fr = new FileReader(filepath + FILENAME);
						BufferedReader br=new BufferedReader(fr);
						String Line=br.readLine();
					
						while(Line!=null){ 
							ls.add(Line);
							Line=br.readLine();
						} 
						br.close();
						fr.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						
						e.printStackTrace();
					} 
					
					
							ls.remove(0);			//删除最旧的一条数据，插入新数据
					try{
							FileOutputStream os02=openFileOutput("query_sms.txt", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE); 
					        Iterator<String> it=ls.iterator();
					        while(it.hasNext())
					        {
					        	String str=it.next()+"\n";
					        	byte[] bu= str.getBytes();
					        	os02.write(bu);
					        }
					        os02.write(content.getBytes());  
					        os02.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
	
	
/*××××××××××××××××××××启动查询应用程序×××××××××××××××××××××××××××××××××××××××××*/
		ComponentName com=new ComponentName("com.kld","com.kld.KldMap");
		intent.setComponent(com);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		LocationDataSaveService.this.startActivity(intent);
		
		stopSelf();
		return START_REDELIVER_INTENT ;
}
	
		
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}


