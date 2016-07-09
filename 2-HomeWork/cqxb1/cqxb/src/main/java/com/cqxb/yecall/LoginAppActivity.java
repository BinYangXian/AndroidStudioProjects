package com.cqxb.yecall;

import static android.content.Intent.ACTION_MAIN;

import java.util.List;

import org.linphone.LinphoneManager;
import org.linphone.LinphoneService;
import org.linphone.core.OnlineStatus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.CommReply;
import com.cqxb.yecall.bean.SipAccountBean;
import com.cqxb.yecall.t9search.model.Contacts;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.ContactBase;
import com.cqxb.yecall.until.PhoneCallAuthUtil;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;
import com.cqxb.yecall.until.T;

public class LoginAppActivity extends BaseTitleActivity implements OnClickListener{
	
	private LinearLayout formlogin_layout1,loginPage;
	private EditText ediphonenum,edipwd;
	private Button loginButton,registUser;
	private String TAG="LoginActivity";
	private TextView forgetPwd;
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		String checkLogin = SettingInfo.getParams(PreferenceBean.CHECKLOGIN, "");
		String loginFlag = SettingInfo.getParams(PreferenceBean.LOGINFLAG, "");
		if(!"".equals(loginFlag)){
			handler.sendEmptyMessage(0);
		}
		setContentView(R.layout.activity_applogin);
		setTitle("登录");
		//页面
		formlogin_layout1=(LinearLayout)findViewById(R.id.formlogin_layout1);
		loginPage=(LinearLayout)findViewById(R.id.loginPage);
		//文本框
		ediphonenum=(EditText)findViewById(R.id.ediphonenum);
		edipwd=(EditText)findViewById(R.id.edipwd);
		//按钮
		loginButton=(Button)findViewById(R.id.loginButton);
		registUser=(Button)findViewById(R.id.registUser);
		//忘记密码
		forgetPwd=(TextView)findViewById(R.id.forgetPwd);
		//设置点击事件
		loginButton.setOnClickListener(this);
		registUser.setOnClickListener(this);
		forgetPwd.setOnClickListener(this);
		//赋值
		ediphonenum.setText(SettingInfo.getParams(PreferenceBean.USERACCOUNT, ""));
		edipwd.setText(SettingInfo.getParams(PreferenceBean.USERPWD, ""));
		//初始化context
		YETApplication.instanceContext(getApplicationContext());
		//初始化存储
		SettingInfo.init(getApplicationContext());
		//初始化来电状态
		new PhoneCallAuthUtil().callState();
		Log.i("", "LOGINFLAG=["+BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.LOGINFLAG, ""))+"]");
		if(!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEIP, "")))&&
				!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEPORT, "")))&&
				!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEACCOUNT, "")))&&
				!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERLINPHONEPWD, "")))&&
				!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERACCOUNT, "")))&&
				!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.USERPWD, ""))) &&
				!"".equals(BaseUntil.stringNoNull(SettingInfo.getParams(PreferenceBean.LOGINFLAG, "")))
				){
			startActivity(new Intent(LoginAppActivity.this,MainTabActivity.class));
		}
//		new NotificationUtil().setNotification(0, LoginAppActivity.class, R.drawable.tb144_144, getString(R.string.app_name),"未注册");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:	
				//compalete完成后获取得到的组织架构和配置自己的im以及sip
				Intent intent = new Intent(LoginAppActivity.this, MainTabActivity.class);
				startActivity(intent);
//				formlogin_layout1.setVisibility(View.GONE);
				finish();
				break;
			case 1:
				loginPage.setVisibility(View.VISIBLE);
				formlogin_layout1.setVisibility(View.GONE);
				break;
			case 999999:
				try {
					if(dialog!=null)dialog.dismiss();
					exit();
					YETApplication.getinstant().exit();
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			try {
				dialog=ProgressDialog.show(LoginAppActivity.this, "", "正在退出...");
				dialog.show();
				handler.sendEmptyMessage(999999);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;  
	}
	
	/**
	//监听按键
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			// 创建退出对话框 
			AlertDialog.Builder isExit = new AlertDialog.Builder(this); 
			// 设置对话框标题  
			isExit.setTitle("系统提示");
			// 设置对话框消息  
		    isExit.setMessage("确定要退出吗");
		    // 添加选择按钮并注册监听  
		    isExit.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss(); 
					exit();
					YETApplication.getinstant().exit();
					finish();
				}
			});
		    isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 dialog.dismiss(); 
				}
			});
		    // 显示对话框
		    isExit.show();  
		}
		return false;  
	}
	**/
	
	public void exit() {
		refreshStatus(OnlineStatus.Offline);
		finish();
		stopService(new Intent(ACTION_MAIN).setClass(this, LinphoneService.class));
	}
	
	private void refreshStatus(OnlineStatus status) {
		if (LinphoneManager.isInstanciated()) {
			LinphoneManager.getLcIfManagerNotDestroyedOrNull().setPresenceInfo(0, "", status);
		}
	}

	@Override
	public void onClick(View v) {
		try {
			if(v.getId()==R.id.loginButton){
				if("".equals(ediphonenum.getText().toString())){
					T.show(getApplicationContext(), "请输入手机号！", 0);
					return ;
				}
				if("".equals(edipwd.getText().toString())){
					T.show(getApplicationContext(), "请输入密码！", 0);
					return ;
				}
				if(edipwd.getText().length()<6){
					T.show(getApplicationContext(), "密码不小于6位！", 0);
					return ;
				}
				loginPage.setVisibility(View.GONE);
				formlogin_layout1.setVisibility(View.VISIBLE);
				SettingInfo.setParams(PreferenceBean.USERACCOUNT, ediphonenum.getText().toString());
				SettingInfo.setParams(PreferenceBean.USERPWD, edipwd.getText().toString());
				SettingInfo.setParams(PreferenceBean.LOGINFLAG, "");
				new NetAction().setLogin(new OnMyResponseListener() {
					@Override
					public void onResponse(String jsonObject) {
						if(!"".equals(BaseUntil.stringNoNull(jsonObject))){
							SipAccountBean bean=JSONObject.parseObject(jsonObject.toString(), SipAccountBean.class);
							if(CommReply.SUCCESS.equals(bean.getStatuscode())){
								SettingInfo.setParams(PreferenceBean.LOGINFLAG, "true");
								//sip 登录信息
								SettingInfo.setParams(PreferenceBean.USERLINPHONEIP, bean.getIpaddr());
								SettingInfo.setParams(PreferenceBean.USERLINPHONEPORT, bean.getPort());
								SettingInfo.setLinphoneAccount(bean.getSipaccount());
								SettingInfo.setLinphonePassword(bean.getSippassword());
								getCallLogs();
								handler.sendEmptyMessage(0);
							}else {
								T.show(getApplicationContext(), ""+bean.getReason(), 0);
								handler.sendEmptyMessage(1);
							}
						}else {
							T.show(getApplicationContext(), getString(R.string.service_error), 0);
							handler.sendEmptyMessage(1);
						}
						
						
					}
				}).execm();
			}else if(v.getId()==R.id.registUser){
				//跳转到激活页面
				Intent intent=new Intent(this,RegistUserVoiceActivity.class);
				startActivity(intent);
			}else if(v.getId()==R.id.forgetPwd){
				//跳转到修改密码页面
				Intent forget=new Intent(this,ForgetPwdActivity.class);
				startActivity(forget);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "异常："+e.getLocalizedMessage());
		}
	}
	
	/**
	 * 获取通话记录
	 */
	public void getCallLogs(){
		System.out.println("插入成功:::::::::::"+SettingInfo.getAccount());
		try {
			// 通话记录
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
