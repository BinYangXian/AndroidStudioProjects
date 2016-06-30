package com.jikexueyuan.playmp4_112;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_play_intent, btn_play_videoView;
    private VideoView main_videoView;
    private String fileName = "mv.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!fileExist(fileName)) {
            copyToMobile(fileName);
            Log.i("Note", "准备拷贝");
        }

        btn_play_intent = (Button) findViewById(R.id.btn_play_intent);
        btn_play_videoView = (Button) findViewById(R.id.btn_play_videoView);
        main_videoView = (VideoView) findViewById(R.id.main_videoView);
        btn_play_intent.setOnClickListener(this);
        btn_play_videoView.setOnClickListener(this);
    }

    //------------拷贝mp4到手机中---------------
    private String getDir() {

        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/myplayer/";
    }

    private boolean fileExist(String fileName) {
        File file = new File(getDir() + fileName);
        if (file.exists()) {
            Log.i("Note", file.getAbsolutePath());
            return true;
        }
        return false;
    }

    private void copyToMobile(final String fileName) {

        new Thread() {
            @Override
            public void run() {
                File dir = new File(getDir());
                if (!dir.exists()) {
                    dir.mkdir();
                }
                InputStream fis = null; //fis（输入流）是指从一个字节流中读取数据 的对象。（用于读取文件中的数据流
                OutputStream fos = null;//fos（输出流）是指将字节流数据写入目标 的对象。（用于把数据写入到文件中
                //从资源文件中获取到了raw下的music.MP3到输入流，可以用来把数据读取出来
                fis = getResources().openRawResource(R.raw.mv);//从这个（赋值号右侧的）字节流读取数据
                //创建一个被写入数据到文件
                File to = new File(getDir(), fileName);
                try {
                    Log.i("Note", "开始拷贝");

                    fos = new FileOutputStream(to);//创建一个字节输出流来把数据写入到to（所指文件中） 的对象。

                    byte[] buf = new byte[4096];//临时创建一个数组来存储数据，作为下边的缓存.
                    while (true) {//循环读取（每次读取4k字节）缓存在buf[]中，然后再将buf[]依次写入到输出流fos中，直到fis为空（r==-1）。
                        int r = fis.read(buf);
                        if (r == -1) {
                            Log.i("Note", "拷贝完毕");
                            break;
                        }
                        fos.write(buf);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("Note", "IOException");
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_intent:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + getDir() + fileName), "video/mp4");
                startActivity(intent);
                break;

            case R.id.btn_play_videoView:
                main_videoView.setVideoPath(getDir() + fileName);
                MediaController controller=new MediaController(this);
                main_videoView.setMediaController(controller);
                main_videoView.start();
                break;
        }
    }
}

