package com.cqxb.yecall;


import static android.content.Intent.ACTION_MAIN;

import java.util.List;

import org.linphone.LinphoneActivity;
import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.core.OnlineStatus;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

public class MainTabActivity extends ActivityGroup{
	private TabHost tabHost;
	ExpandableListView expandablelistview;  
	private int flag=1;
	private String TAG="MainTabActivity";
	private CheckBox buttonPan;
	private ToggleButton buttonContacts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//处理用户信息数据
		SettingInfo.init(getApplicationContext());
		
		setContentView(R.layout.activity_tab_main);
		
		getCallLogs();
		
		tabHost = (TabHost) findViewById(R.id.myTabHost);
		//初始化 
		YETApplication.getinstant().addActivity(this);
		addTab();
		//初始化存储
		SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "loginOk");
		//通知栏点击
		buttonPan=(CheckBox)findViewById(R.id.buttonPan);
		buttonPan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				LinphoneActivity.instance().setDialerNumberPanVisiable(isChecked);
				//为了让拨号图片与拨号盘上隐藏号码盘的图标同步
				boolean hideStatus = LinphoneActivity.instance().getHideStatus();
				ImageView iv = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(R.id.image);
				Bitmap bohao_hover = BitmapFactory.decodeResource(getResources(), R.drawable.bohao_hover);
				Bitmap bohao_hoverdown = BitmapFactory.decodeResource(getResources(), R.drawable.bohao_hoverdown);
				if(!hideStatus){
					iv.setImageBitmap(bohao_hover);
				}else {
					iv.setImageBitmap(bohao_hoverdown);
				}
			}
		});
		
		buttonContacts=(ToggleButton)findViewById(R.id.buttonContacts);
		buttonContacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_INSERT);
				intent.setType("vnd.android.cursor.dir/person");
				intent.setType("vnd.android.cursor.dir/contact");
				intent.setType("vnd.android.cursor.dir/raw_contact");
				String inputNumber = LinphoneActivity.instance().getInputNumber();
				intent.putExtra("phone", inputNumber); 
				startActivity(intent);
//				startActivityForResult(intent, 0);
			}
		});
		registerReceiver(broadcastReceiver, new IntentFilter(Smack.action));
		Log.e("", "MainTabActivity  oncreate");
	}

//	@Override
//	public void onActivityResult(int reqCode, int resultCode, Intent data) {
//	  super.onActivityResult(reqCode, resultCode, data);
//	  Log.e("", "===========:"+reqCode+"        "+resultCode+"    ");
//	  //保存完成
//	}
	
	
	/**
	 * 添加选项卡
	 */
	private void addTab(){
        // 如果不是继承TabActivity，则必须在得到tabHost之后，添加标签之前调用tabHost.setup()
		//tabHost.setup(); 
		tabHost.setup(this.getLocalActivityManager());//不写这句话会报错
		/* 去除标签下方的白线 */
		tabHost.setPadding(tabHost.getPaddingLeft(), tabHost.getPaddingTop(), tabHost.getPaddingRight(), tabHost.getPaddingBottom() - 5);
        
		Intent layout0intent = new Intent();
		layout0intent.setClass(this, InformationActivity.class);
        TabHost.TabSpec mDialerTabSpec1 = tabHost.newTabSpec("dialer1");
        mDialerTabSpec1.setIndicator(getTabView(R.drawable.tab_public));
		mDialerTabSpec1.setContent(layout0intent);
//        mDialerTabSpec1.setContent(R.id.view1);
		//消息
//		tabHost.addTab(mDialerTabSpec1);
		
		
		//拨号
		Intent layout2intent = new Intent();
//		layout2intent.setClass(this, DialingActivity.class);
		layout2intent.setClass(this, LinphoneActivity.class);
		TabHost.TabSpec mDialerTabSpec3 = tabHost.newTabSpec("dialer3");
		mDialerTabSpec3.setIndicator(getTabView("拨号", R.drawable.tab_dialer_up));
		mDialerTabSpec3.setContent(layout2intent);
//		mDialerTabSpec3.setContent(R.id.view3);
		tabHost.addTab(mDialerTabSpec3);
			
		
		//联系人
		Intent layout1intent = new Intent();
//		layout1intent.setClass(this, FriendGroupShow.class);
		layout1intent.setClass(this, NewsletterActivity.class);
		Bundle bundle = new Bundle();
		layout1intent.putExtras(bundle);
		TabHost.TabSpec mDialerTabSpec2= tabHost.newTabSpec("dialer2");
		mDialerTabSpec2.setIndicator(getTabView("联系人",R.drawable.tab_contacts));
		mDialerTabSpec2.setContent(layout1intent);
//				mDialerTabSpec2.setContent(R.id.view2);
		tabHost.addTab(mDialerTabSpec2);
		
		//应用
		Intent layout7intent = new Intent();
		layout7intent.setClass(this, ApplicationActivity.class);
		TabHost.TabSpec mDialerTabSpec7= tabHost.newTabSpec("dialer7");
		mDialerTabSpec7.setIndicator(getTabView("门禁",R.drawable.tab_application));
		mDialerTabSpec7.setContent(layout7intent);
		tabHost.addTab(mDialerTabSpec7);		
		
		
		Intent layout6intent = new Intent();
//		layout6intent.setClass(this, MoreActivity.class);
		layout6intent.setClass(this, PersonCenterActivity.class);
		TabHost.TabSpec mDialerTabSpec4= tabHost.newTabSpec("dialer4");
		mDialerTabSpec4.setIndicator(getTabView("个人中心",R.drawable.tab_setting));
		mDialerTabSpec4.setContent(layout6intent);
//		mDialerTabSpec4.setContent(R.id.view3);
		tabHost.addTab(mDialerTabSpec4);
		
      //创建tab
      	tabHost.setCurrentTab(0);
      	((TextView)tabHost.getTabWidget().getChildAt(0).findViewById(R.id.title)).setTextColor(Color.parseColor("#1D88CE"));
      	
      	tabHost.getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tabHost.setCurrentTab(0);
				boolean hideStatus = LinphoneActivity.instance().getHideStatus();
				ImageView iv = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(R.id.image);
				Bitmap bohao_hover = BitmapFactory.decodeResource(getResources(), R.drawable.bohao_hover);
				Bitmap bohao_hoverdown = BitmapFactory.decodeResource(getResources(), R.drawable.bohao_hoverdown);
				if(!hideStatus){
					iv.setImageBitmap(bohao_hover);
				}else {
					iv.setImageBitmap(bohao_hoverdown);
				}
				// TODO Auto-generated method stub
				Log.v("", "tabchild 0 onClick, currentTab:" + tabHost.getCurrentTab());
				if (tabHost.getCurrentTab() == 0) {
					if (LinphoneActivity.instance() != null) {
						flag++;
						if(flag<=1){
							return;
						}
						boolean setDialerNumberPanVisiable = LinphoneActivity.instance().setDialerNumberPanVisiable();
						if(setDialerNumberPanVisiable){
							iv.setImageBitmap(bohao_hover);
							buttonPan.setChecked(true);
						}else {
							buttonPan.setChecked(true);
							iv.setImageBitmap(bohao_hoverdown);
						}
						
					}
				} 
			}
		});
      	
      	tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				TextView text=(TextView)tabHost.getTabWidget().getChildAt(0).findViewById(R.id.title);
				TextView text1=(TextView)tabHost.getTabWidget().getChildAt(1).findViewById(R.id.title);
				TextView text2=(TextView)tabHost.getTabWidget().getChildAt(2).findViewById(R.id.title);
				TextView text3=(TextView)tabHost.getTabWidget().getChildAt(3).findViewById(R.id.title);
				if(!"dialer3".equals(tabId)){
					ImageView iv = (ImageView)tabHost.getTabWidget().getChildAt(0).findViewById(R.id.image);
					Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.bohao);
					iv.setImageBitmap(decodeResource);
					flag=0;
					findViewById(R.id.call_img).setVisibility(View.GONE);
					LinphoneActivity.instance().clearAddress();
				}
				if("dialer3".equals(tabId)){
					text.setTextColor(Color.parseColor("#1D88CE"));
					text1.setTextColor(Color.parseColor("#ffffff"));
					text2.setTextColor(Color.parseColor("#ffffff"));
					text3.setTextColor(Color.parseColor("#ffffff"));
				}else if("dialer2".equals(tabId)){
					text1.setTextColor(Color.parseColor("#1D88CE"));
					text2.setTextColor(Color.parseColor("#ffffff"));
					text.setTextColor(Color.parseColor("#ffffff"));
					text3.setTextColor(Color.parseColor("#ffffff"));
				}else if("dialer7".equals(tabId)){
					text2.setTextColor(Color.parseColor("#1D88CE"));
					text3.setTextColor(Color.parseColor("#ffffff"));
					text.setTextColor(Color.parseColor("#ffffff"));
					text1.setTextColor(Color.parseColor("#ffffff"));
				}else if("dialer4".equals(tabId)){
					text3.setTextColor(Color.parseColor("#1D88CE"));
					text1.setTextColor(Color.parseColor("#ffffff"));
					text.setTextColor(Color.parseColor("#ffffff"));
					text2.setTextColor(Color.parseColor("#ffffff"));
				}
			}
		});
	}
	
	public View getTabView(String label, int resId) {
		final LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.tab_styles, null).findViewById(R.id.layoutTabStyle);
		((ImageView) (layout.findViewById(R.id.image))).setImageResource(resId);
		((TextView) (layout.findViewById(R.id.title))).setText(label);
		Log.i("agent", "tab style height:" + layout.getHeight());
		return layout;
	}
	
	
	public View getTabView(int resId) {
		final LayoutInflater inflater = LayoutInflater.from(this);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.tab_style, null).findViewById(R.id.layoutTabStyle);
		((ImageView) (layout.findViewById(R.id.image))).setImageResource(resId);
		Log.i("agent", "tab style height:" + layout.getHeight());
		return layout;
	}
	
		
	public void setDialerPanVisibility(int visibility) {
		findViewById(R.id.call_img).setVisibility(visibility);
	}
	
	/**
	 * 调用拨打电话功能
	 * @param view
	 */
	public void callPhone(View view){
		Log.i("TabMain", "TabMain 拨号");
		Activity currentActivity = getCurrentActivity();
		if (currentActivity instanceof LinphoneActivity) {
			((LinphoneActivity)currentActivity).callOut();
		}
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(broadcastReceiver);
		SettingInfo.setParams(PreferenceBean.USERLINPHONEREGISTOK, "");
		SettingInfo.setParams(PreferenceBean.CHECKLOGIN, "");
		super.onDestroy();
		if (LinphoneManager.isInstanciated()) {
			LinphoneManager.getLcIfManagerNotDestroyedOrNull().setPresenceInfo(0, "", OnlineStatus.Offline);
		}
//		new NotificationUtil().clearNotification(getApplicationContext(), 20);
//		new NotificationUtil().clearNotification(getApplicationContext(), 30);
//		if(LinphoneService.instance()!=null)LinphoneService.instance().deleteOldAccount();
		stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
		finish();
	}
	
	private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if("backMune".equals(intent.getStringExtra("backMune"))){
//				startActivity(new Intent()
//				.setAction(Intent.ACTION_MAIN)
//				.addCategory(Intent.CATEGORY_HOME));
				 Intent in = new Intent(Intent.ACTION_MAIN);  
				 in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意  
				 in.addCategory(Intent.CATEGORY_HOME);  
		         startActivity(in);  
			}
		}
	};
	
	/**
	 * 获取通话记录
	 */
	public void getCallLogs(){
		System.out.println("插入成功:::::::::::"+SettingInfo.getAccount());
		try {
			// 通话记录
			if(BaseUntil.stringNoNull(SettingInfo.getAccount()).trim().equals("")){
				return;
			}
			List<Contacts> clList = YETApplication.getinstant().getThjl();
			clList.clear();
			if (clList.size() <= 0) {
				ContactBase cb = new ContactBase(getApplicationContext());
				List<Contacts> allcontact = cb.getPhoneCallLists();
				YETApplication.getinstant().setThjl(allcontact);
			}
		} catch (Exception e) {
			Log.e("", "loginAppActivity  line 380 ===>>>  "+e.getLocalizedMessage());
		}
		
	}
	
}
