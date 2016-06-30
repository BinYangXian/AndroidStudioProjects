package com.jikexueyuan.mucapture_114;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_capture;
    private ImageView img_capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_capture = (Button) findViewById(R.id.btn_capture);
        img_capture = (ImageView) findViewById(R.id.img_capture);

        btn_capture.setOnClickListener(this);
    }

    private final int TAKE_CAPTURE = 1;
    private Uri outPutUri;

    @Override
    public void onClick(View v) {
        File file = new File(Environment.getExternalStorageDirectory(), "my.png");

        outPutUri = Uri.fromFile(file);//或者用下边的方法
//        outPutUri=Uri.parse("file://"+ file.getPath());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍照所得文件存放在outPutUri所指向的外部存储
        startActivityForResult(intent, TAKE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_CAPTURE) {
                if (data != null) {
                    if (data.hasExtra("data")) {//如果dataIntent中存有"data"这个key，那么下边就通过key获取序列化的value。
                        Bitmap bitmap = data.getParcelableExtra("data");//Intent可以传递基本的数据类型，
                        // 与序列化的数据类型，Bitmap便是其一。
                        img_capture.setImageBitmap(bitmap);//将相机获取到的Bitmap数据流，通过Intent传给img_capture
                    }
                } else {
                    int width = img_capture.getWidth();
                    int height = img_capture.getHeight();

                    //由于直接解析图片，会消耗很多系统资源，所以用下述：
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;//仅仅需要解析图片的宽高等。
                    BitmapFactory.decodeFile(outPutUri.getPath(), options);//具体 解析图片的宽高等。

                    int imgWidth = options.outWidth;
                    int imgHeight = options.outHeight;

                    int scale = Math.min(imgWidth / width, imgHeight / height);//为了尽可能保持清晰度取二者最小值
                    scale = scale == 0 ? 1 : scale;//如果scale等于0时赋值为1（不缩放），否则保持不变

                    options.inJustDecodeBounds=false;//仅仅需要解析图片的宽高等？设为不是
                    options.inSampleSize=scale;//设置缩放系数
                    Bitmap bitmap=BitmapFactory.decodeFile(outPutUri.getPath(),options);
                    img_capture.setImageBitmap(bitmap);
                }
            }
        }
    }
}
