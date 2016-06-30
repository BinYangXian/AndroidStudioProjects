package com.jikexueyuan.simpleimagebrowse;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnOpenImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImage();
            }
        });
    }
//此法不够简，下述有二法
    private void showImg() {
        //得到外部存储卡的路径
        File sdCard = Environment.getExternalStorageDirectory();
        //将图片命名并确定存储位置
        File file = new File(sdCard.getAbsolutePath(), "beauty.jpg");
        //从资源文件中选择一张图片作为将要写入目标的数据来源文件
        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.a);
        try {
            FileOutputStream out = new FileOutputStream(file);//打开file所指文件，以这个文件作为流的储存载体（储存水池）的out对象。
            // file所指文件是存储的抽象路径名为sdCard.getAbsolutePath()方法的返回值、文件名为"beauty.jpg"。
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);//把缓冲区里的内容按规则压缩后写到具体的

//            byte[] buffer=new byte[1024];
//            Drawable drawable=getResources().getDrawable(R.drawable.a,null);
//            drawable.cre
//            out.write(); //基本的复制暂时不会呀，就用bitmap吧。
            // 设备（sdCard的绝对路径的"beauty.jpg"）里去。
            out.flush();//清空缓冲区
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //隐式Intent
        Intent intent = new Intent(Intent.ACTION_VIEW);

        Uri mUri = Uri.parse("file://" + file.getPath());
        intent.setDataAndType(mUri, "image/*");
        startActivity(intent);
    }
//
    private void showImage() {
        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.a);
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));//因使用getContentResolver方法。此时uri的scheme应为 content,
        //因为当android:scheme="file"时，ImageActivity无法通过filter；而android:scheme="content"时（或者没有设置scheme时，这又是另外一件事了）能通过 。
        //隐式Intent
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }
}
