package com.cqxb.yecall;

import com.android.action.param.NetParam;
import com.cqxb.yecall.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProtocolActivity extends BaseTitleActivity{

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
		setContentView(R.layout.activity_protocol);
		setTitle("关于我们");
		setCustomLeftBtn(R.drawable.fanhui,new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		webView = (WebView) findViewById(R.id.webView);
		
		String url = NetParam.SERVER+NetParam.SERVER_NAME+"/protocol.htm";
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new MyWebViewClient());
		progressDlg = ProgressDialog.show(ProtocolActivity.this, null, "请等待...", true, true);
		progressDlg.show();
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
