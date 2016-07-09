package com.cqxb.yecall;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ImageDetailActivity extends Activity {

	Bitmap bp = null;
	ImageView imageview;
	float scaleWidth;
	float scaleHeight;

	int h;
	boolean num = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String imgUrl = getIntent().getStringExtra("imgUrl");
		setContentView(R.layout.activity_image_dtail);
		Display display = getWindowManager().getDefaultDisplay();
		imageview = (ImageView) findViewById(R.id.iv_image_detail);
		bp = BitmapFactory.decodeFile(imgUrl);
		int width = bp.getWidth();
		int height = bp.getHeight();
		int w = display.getWidth();
		int h = display.getHeight();
		scaleWidth = ((float) w) / width;
		scaleHeight = ((float) h) / height;
		//imageview.setImageBitmap(bp);
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
				bp.getHeight(), matrix, true);
		imageview.setImageBitmap(newBitmap);
		num = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_detail, menu);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			
		case MotionEvent.ACTION_DOWN:
//			if (num == true) {
//				Matrix matrix = new Matrix();
//				matrix.postScale(scaleWidth, scaleHeight);
//
//				Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
//						bp.getHeight(), matrix, true);
//				imageview.setImageBitmap(newBitmap);
//				num = false;
//			} else {
//				Matrix matrix = new Matrix();
//				matrix.postScale(1.0f, 1.0f);
//				Bitmap newBitmap = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(),
//						bp.getHeight(), matrix, true);
//				imageview.setImageBitmap(newBitmap);
//				num = true;
//			}
			finish();
			break;
		}

		return super.onTouchEvent(event);
	}

}
