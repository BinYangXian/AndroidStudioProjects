package com.jikexueyuan.playmp3correctly;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button btn_play_raw;
    private Button btn_stop_play;
    private Button btn_pause_play;
    private Button btn_play_intent;
    private MediaPlayer mediaPlayer;

    private String fileName = "music.mp3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play_raw = (Button) findViewById(R.id.btn_play_raw);
        btn_stop_play = (Button) findViewById(R.id.btn_stop_play);
        btn_pause_play = (Button) findViewById(R.id.btn_pause_play);
        btn_play_intent = (Button) findViewById(R.id.btn_play_intent);
        btn_play_raw.setOnClickListener(this);
        btn_stop_play.setOnClickListener(this);
        btn_pause_play.setOnClickListener(this);
        btn_play_intent.setOnClickListener(this);

        if (!fileExist(fileName)) {
            copyToMobile(fileName);
            Log.i("Note", "准备拷贝");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_raw:
                if (mediaPlayer == null) {

                    mediaPlayer = MediaPlayer.create(this, R.raw.music);
                    mediaPlayer.start();
                }
                break;
            case R.id.btn_play_intent:
                //通过隐式intent启动系统播放程序：
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + getDir() + fileName), "audio/mp3");
                startActivity(intent);
                break;
            case R.id.btn_stop_play:
                mediaPlayer.stop();
                mediaPlayer.release();//释放资源
                mediaPlayer = null;
                btn_pause_play.setText("暂停播放");
                break;
            case R.id.btn_pause_play:
                if (mediaPlayer != null) {

                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        btn_pause_play.setText("继续播放");
                    } else if (btn_pause_play.getText().equals("继续播放")) {
                        mediaPlayer.start();
                        btn_pause_play.setText("暂停播放");
                    }
                }
                break;
        }
    }

    //------------拷贝mp3到手机中---------------
    private String getDir() {

        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/myplayer/";
    }

    private boolean fileExist(String fileName) {
        File file = new File(getDir() + fileName);
        if (file.exists()) {
            Log.i("Note","已经存在music.mp3");
            Log.i("Note",file.getAbsolutePath());
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
                InputStream fis = null;//用于读取文件中的数据流
                OutputStream fos = null;//用于把数据写入到文件中
                //从资源文件中获取到了raw下的music.MP3到输入流，可以用来把数据读取出来
                fis = getResources().openRawResource(R.raw.music);
                //创建一个被写入数据到文件
                File to = new File(getDir(), fileName);
                try {
                    Log.i("Note","开始拷贝");
                    // 创建一个文件输出流来把数据写入到文件to中
                    fos = new FileOutputStream(to);

                    byte[] buf = new byte[4096];//临时创建一个数组来存储数据
                    while (true) {//循环读取（每次读取4kb），然后通过buf写入到输出流fos中，直到fis为空（r==-1）。
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

}
