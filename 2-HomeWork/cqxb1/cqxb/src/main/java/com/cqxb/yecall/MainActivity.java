package com.cqxb.yecall;

import static android.content.Intent.ACTION_MAIN;

import java.io.File;
import java.util.List;

import org.linphone.LinphoneActivity;
import org.linphone.LinphoneService;
import org.linphone.mediastream.Log;
import org.linphone.tutorials.TutorialLauncherActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;

import com.cqxb.yecall.bean.CallLogBean;
import com.cqxb.yecall.bean.ContactEntity;
import com.cqxb.yecall.bean.GroupChatEntity;
import com.cqxb.yecall.bean.InformationList;
import com.cqxb.yecall.bean.SingleChatEntity;
import com.cqxb.yecall.db.DBHelper;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
public class MainActivity extends Activity {
	private DBHelper dbHelper;
	private Handler mHandler;
	private ServiceWaitThread mThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Used to change for the lifetime of the app the name used to tag the logs
		new Log(getResources().getString(R.string.app_name), !getResources().getBoolean(R.bool.disable_every_log));
		
		// Hack to avoid to draw twice LinphoneActivity on tablets
		if (getResources().getBoolean(R.bool.isTablet)) {
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
		setContentView(R.layout.activity_main);
		
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				dbHelper=new DBHelper(getApplicationContext());
				if (LinphoneService.isReady()) {
					initSql();//sql数据库
				} else {
					startService(new Intent(ACTION_MAIN).setClass(MainActivity.this, LinphoneService.class));
					mThread = new ServiceWaitThread();
					mThread.start();
				}
			}
		}, 1000);
	}

	/**
	 * 初始化数据脚本
	 */
	private void initSql() {
		
		File f = new File(Environment.getExternalStorageDirectory()+YETApplication.getContext().getString(R.string.chat_pathimg));
		
		System.out.println("ChatListener "+f.getAbsolutePath());
		System.out.println("ChatListener "+f.getPath());
		if(!f.exists()){
			f.mkdir();
		}
		
		YETApplication.instanceContext(getApplicationContext());
		SettingInfo.init(getApplicationContext());
		String back = SettingInfo.getParams(PreferenceBean.IS_FIRST_CREATE_DATA_SQL, "第一次登陆");
		System.out.println("initSql " + back + "!!!!!!!!!!!!!!!!!!!!!!!!");
		if ("第一次登陆".equals(back)) {
			SettingInfo.setParams(PreferenceBean.IS_FIRST_CREATE_DATA_SQL, "创建系统表");
			DBHelper dbHelper = new DBHelper(getApplicationContext());
			dbHelper.open();
			// 好友消息列表
			String singleChat = "DROP TABLE IF EXISTS "+SingleChatEntity.TABLE;
			// 聊天记录
			String groupChat = "DROP TABLE IF EXISTS "+GroupChatEntity.TABLE;
			// 联系人列表
			String information = "DROP TABLE IF EXISTS "+InformationList.TABLE;
			// 群聊
			String contact = "DROP TABLE IF EXISTS "+ContactEntity.TABLE;
			// 通话记录
			String callLog = "DROP TABLE IF EXISTS "+CallLogBean.TABLE;

			dbHelper.execSql(singleChat, null);
			dbHelper.execSql(groupChat, null);
			dbHelper.execSql(information, null);
			dbHelper.execSql(contact, null);
			dbHelper.execSql(callLog, null);
			
			// 单人聊天
			String singleChatCreate = "CREATE TABLE  IF NOT EXISTS "+SingleChatEntity.TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
					SingleChatEntity.USERID+" text , "+
					SingleChatEntity.FRIENDID+" text  , "+
					SingleChatEntity.CONTEXT+" text,"+
					SingleChatEntity.WHO+" text,"+
					SingleChatEntity.ISREAD+" text, "+
					SingleChatEntity.NICKNAME+" text,"+
					SingleChatEntity.MSGDATE+" text)";
			// 群聊
			String groupChatCreate = "CREATE TABLE  IF NOT EXISTS "+GroupChatEntity.TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
					GroupChatEntity.ROOMID+" text , "+
					GroupChatEntity.FRIENDID+" text  , "+
					GroupChatEntity.CONTEXT+" text,"+
					GroupChatEntity.WHO+" text,"+
					GroupChatEntity.ISREAD+" text, "+
					GroupChatEntity.NICKNAME+" text,"+
					GroupChatEntity.GID+" text,"+
					GroupChatEntity.MSGDATE+" text)";
			// 消息列表
			String informationCreate = "CREATE TABLE  IF NOT EXISTS "+InformationList.TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
					InformationList.GID+" text , "+
					InformationList.FRIENDID+" text , "+
					InformationList.ROOMID+" text , "+
					InformationList.NICKNAME+" text, "+
					InformationList.CONTEXT+" text,"+
					InformationList.MSGDATE+" text,"+
					InformationList.FRIENDIMG+" text,"+
					InformationList.OBJECT+" text,"+
					InformationList.FLAG+" text)";
			//near "group": syntax error (code 1): , while compiling: 
			//CREATE TABLE  IF NOT EXISTS contactList( _id INTEGER PRIMARY KEY AUTOINCREMENT, 
					//friendId text,nickName text,friendImg text,visibility 
					//text,descr text,group text,visibilityImg text,status text)
			// 联系人
			String contactCreate = "CREATE TABLE  IF NOT EXISTS "+ContactEntity.TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
					ContactEntity.FRIENDID+" text,"+
					ContactEntity.NICKNAME+" text,"+
					ContactEntity.FRIENDIMG+" text,"+
					ContactEntity.VISIBILITY+" text,"+
					ContactEntity.DESCR+" text,"+
					ContactEntity.GROUP+" text,"+
					ContactEntity.VISIBILITYIMG+" text,"+
					ContactEntity.STATUS+" text)";
			
			// 通话记录
			String callLogs = "CREATE TABLE  IF NOT EXISTS "+CallLogBean.TABLE
					+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
					CallLogBean.NAME+" text,"+
					CallLogBean.NUMBER+" text,"+
					CallLogBean.TYPE+" text,"+
					CallLogBean.CALLTIME+" text,"+
					CallLogBean.BELONG+" text,"+
					CallLogBean.NEWS+" text,"+
					CallLogBean.STARTCALL+" text,"+
					CallLogBean.RECORDPATH+" text,"+
					CallLogBean.PHOTOPATH+" text)";
			dbHelper.execSql(singleChatCreate, null);
			dbHelper.execSql(groupChatCreate, null);
			dbHelper.execSql(informationCreate, null);
			dbHelper.execSql(contactCreate, null);
			dbHelper.execSql(callLogs, null);
			System.out.println("插入成功...插入成功...插入成功...插入成功...插入成功...");
			dbHelper.close();
		}
		
		initCallRecord();
	}
	
	/**
	 * 获取手机通讯录
	 */
	public void initCallRecord() {
		// 联系人
		List<Contacts> cltList = YETApplication.getinstant().getCltList();
		ContactBase cb = new ContactBase(getApplicationContext());
		// cb.testAddContact(getApplicationContext(), "在仄仄仄仄仄仄仄仄仄仄仄仄",
		// "1231231231231");
		// ContactBase.insertCallLog(getApplicationContext(), "zzzzzzzzzz",
		// "123456465465", 2, 30, System.currentTimeMillis());
		if (cltList.size() <= 0) {

			List<Contacts> allcontact = cb.getAllcontact();
			YETApplication.getinstant().setCltList(allcontact);
		}
//		// 通话记录
//		List<CallBean> clList = YETApplication.getinstant().getClList();
//		if (clList.size() <= 0) {
//			List<CallBean> allcontact = cb.getPhoneCallList();
//			YETApplication.getinstant().setClList(allcontact);
//		}

		onServiceReady();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	private Handler handler=new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case 1:
//				startActivity(new Intent(MainActivity.this,LoginActivity.class));
//				finish();
//				break;
//
//			default:
//				break;
//			}
//		}
//		
//	};
	
	private class ServiceWaitThread extends Thread {
		public void run() {
			while (!LinphoneService.isReady()) {
				try {
					sleep(30);
				} catch (InterruptedException e) {
					throw new RuntimeException("waiting thread sleep() has been interrupted");
				}
			}

			mHandler.post(new Runnable() {
				@Override
				public void run() {
//					onServiceReady();
					initSql();//sql数据库
				}
			});
			mThread = null;
		}
	}
	
	
	protected void onServiceReady() {
		final Class<? extends Activity> classToStart;
		if (getResources().getBoolean(R.bool.show_tutorials_instead_of_app)) {
			classToStart = TutorialLauncherActivity.class;
		} else {
			classToStart = MainTabActivity.class;
//			classToStart = LoginAppActivity.class;
		}
		
		LinphoneService.instance().setActivityToLaunchOnIncomingReceived(classToStart);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				
				startActivity(new Intent().setClass(MainActivity.this, classToStart).setData(getIntent().getData()));
				finish();
			}
		}, 1000);
	}
}
