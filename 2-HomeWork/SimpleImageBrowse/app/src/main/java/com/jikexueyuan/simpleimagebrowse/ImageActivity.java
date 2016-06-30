package com.jikexueyuan.simpleimagebrowse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        //加载图片
//        ImageView imageView = new ImageView(this);//动态创建imageView对象，与静态功能重复
//        setContentView(imageView);
        imageView = (ImageView) findViewById(R.id.imageView);
        try {
            imageView.setImageURI(getIntent().getData());
        } catch (Exception e) {
            Toast.makeText(this, "不能打开图片", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
