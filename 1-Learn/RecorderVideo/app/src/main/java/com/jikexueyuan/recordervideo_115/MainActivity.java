package com.jikexueyuan.recordervideo_115;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_recorder;
    private Button btn_play;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_recorder = (Button) findViewById(R.id.btn_recorder);
        videoView = (VideoView) findViewById(R.id.videoView);

        btn_play.setOnClickListener(this);
        btn_recorder.setOnClickListener(this);
    }

    private final int VIDEO_RECORDER = 1;
    private Uri outPutUri;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recorder:

                File file=new File(Environment.getExternalStorageDirectory(),"myRecorder.mp4");
                outPutUri=Uri.fromFile(file);
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,outPutUri);//自定义视频录制的存储路径
                startActivityForResult(intent, VIDEO_RECORDER);
                break;
            case R.id.btn_play:

                videoView.setVideoURI(outPutUri);
                videoView.start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
//            outPutUri=data.getData();//从录制视频的系统存储路径 获取uri
        }
    }
}
