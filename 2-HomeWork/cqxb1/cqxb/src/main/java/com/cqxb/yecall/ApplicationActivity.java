package com.cqxb.yecall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.action.param.NetParam;
import com.cqxb.yecall.until.PreferenceBean;
import com.cqxb.yecall.until.SettingInfo;

public class ApplicationActivity extends BaseTitleActivity {
	
	private WebView webView;
	private ProgressDialog progressDlg;
	
	private static final int MSG_FINISH_TASK = 0x01;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_FINISH_TASK:
				if (progressDlg != null)
					progressDlg.dismiss();
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_application);
		setTitle("门禁");
		webView = (WebView) findViewById(R.id.webView);
		
		String url = "http://115.28.2.168/door/appController/autoLgoin?userName="+SettingInfo.getParams(PreferenceBean.USERACCOUNT, "");
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new MyWebViewClient());
		progressDlg = ProgressDialog.show(ApplicationActivity.this, null, "请等待...", true, true);
		progressDlg.show();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK ){
			sendBroadcast(new Intent(Smack.action).putExtra("backMune", "backMune"));
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);

			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			view.getSettings().setJavaScriptEnabled(true);
			super.onPageFinished(view, url);
			mHandler.sendEmptyMessage(MSG_FINISH_TASK);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}
	}
}
