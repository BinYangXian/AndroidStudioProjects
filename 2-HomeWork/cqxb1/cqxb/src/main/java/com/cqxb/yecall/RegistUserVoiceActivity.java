package com.cqxb.yecall;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

import com.alibaba.fastjson.JSONObject;
import com.android.action.NetAction;
import com.android.action.NetBase.OnMyResponseListener;
import com.android.action.param.AuthCodeRequest;
import com.android.action.param.CommReply;
import com.android.action.param.RegisteredRequest;
import com.cqxb.yecall.bean.AuthCodeBean;
import com.cqxb.yecall.bean.SipAccountBean;
import com.cqxb.yecall.until.BaseUntil;
import com.cqxb.yecall.until.T;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegistUserVoiceActivity extends BaseTitleActivity implements OnClickListener{

	private LinearLayout layout1, layout2;
	private EditText editText1, editText3, editText4,yanzhengma;
	private Button getCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_user_voice);
		setTitle("注册账号");
		setCustomLeftBtn(R.drawable.fanhui, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		// 初始化参数
		editText1 = (EditText) findViewById(R.id.editTextPhone);
		editText3 = (EditText) findViewById(R.id.editTextPwd);
		editText4 = (EditText) findViewById(R.id.editTextpwd2);
		yanzhengma= (EditText) findViewById(R.id.yanzhengma);

		layout1 = (LinearLayout) findViewById(R.id.formRegist_layout);
		layout2 = (LinearLayout) findViewById(R.id.formRegist_layout1);
		
		getCode=(Button)findViewById(R.id.getCode);
		getCode.setOnClickListener(this);
		
		findViewById(R.id.buttonOK).setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	

	public void regsitOk() {
		if (editText1.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请输入账号", 1);
			return;
		}
		if (editText3.getText().toString().length() <= 0) {
			T.show(getApplicationContext(), "请输入密码", 1);
			return;
		}
		if (editText3.getText().toString().length() < 6) {
			T.show(getApplicationContext(), "密码至少6位", 1);
			return;
		}
		if (!editText3.getText().toString()
				.equals(editText4.getText().toString())) {
			T.show(getApplicationContext(), "两次输入的密码不一致", 1);
			return;
		}
//		if (yanzhengma.getText().toString().length()<=0) {
//			T.show(getApplicationContext(), "请输入验证码", 1);
//			return;
//		}
		
		RegisteredRequest regist = new RegisteredRequest();
		regist.setAccount(editText1.getText().toString());
		regist.setPassword(editText3.getText().toString());
		regist.setVersion(YETApplication.appClass);
		regist.setAuthcode(yanzhengma.getText().toString());
		handler.sendEmptyMessage(1);
		new NetAction().setRegistered(regist, new OnMyResponseListener() {
			@Override
			public void onResponse(String jsonObject) {
				if (!"".equals(BaseUntil.stringNoNull(jsonObject))) {
					JSONObject parseObject = JSONObject.parseObject(jsonObject);
					if (CommReply.SUCCESS.equals(parseObject
							.getString("statuscode"))) {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
						finish();
					} else {
						T.show(getApplicationContext(),"" + parseObject.getString("reason"), 1);
						handler.sendEmptyMessage(2);
					}
				} else {
					T.show(getApplicationContext(), getString(R.string.service_error), 1);
					handler.sendEmptyMessage(2);
				}
			}
		}).execm();

	}

	// 接受消息控制 显示不同的页面
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
			} else if (msg.what == 2) {
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
			}
		};
	};
	
	private int time=30;
	
	//处理倒计时
	private Runnable runnable = new Runnable( ) {

		public void run ( ) {
			getCode.setText(time+"");
			getCode.setClickable(false);
			handler.postDelayed(this,1000);//postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
			
			
			if(time<=0){
				time=31;//第二次  这里比外面多1是因为按照从上往下的顺序    
				getCode.setClickable(true);
				getCode.setText("获取语音验证码");
				handler.removeCallbacks(runnable);
			}
			time--;//会在这里在减去1
		}

	};

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.getCode){
			if (editText1.getText().toString().length() <= 0) {
				T.show(getApplicationContext(), "请输入账号", 1);
				return;
			}
			if (editText1.getText().toString().length() < 11) {
				T.show(getApplicationContext(), "手机号最小长度为11个字符！", 0);
				return;
			}
			getCode.setText(""+time);
			getCode.setClickable(false);
			handler.postDelayed(runnable, 0);
			
			T.show(RegistUserVoiceActivity.this, "请稍候",0);
			AuthCodeRequest authCode=new AuthCodeRequest();
			authCode.setAccount(editText1.getText().toString());
			authCode.setVersion(YETApplication.appClass);
			authCode.setType("0");
			new NetAction().authCode(authCode, new OnMyResponseListener() {
				
				@Override
				public void onResponse(String jsonObject) {
					if(!"".equals(BaseUntil.stringNoNull(jsonObject))){
						AuthCodeBean bean=JSONObject.parseObject(jsonObject.toString(), AuthCodeBean.class);
						if(CommReply.SUCCESS.equals(bean.getStatuscode())){
							yanzhengma.setText("");
						}else {
							T.show(getApplicationContext(), bean.getReason(), 0);
							yanzhengma.setText("");
						}
					}else {
						T.show(getApplicationContext(), getString(R.string.service_error), 0);
						yanzhengma.setText("");
					}
				}
			}).execm();
		}else if(v.getId()==R.id.buttonOK){
			regsitOk();
		}
	}
}
