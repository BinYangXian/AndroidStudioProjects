package com.jikexueyuan.audiorecorder_1131;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_recorder,btn_stop_recorder,btn_play;
    private AudioRecorder audioRecorder;
    private AudioPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_recorder= (Button) findViewById(R.id.btn_recorder);
        btn_stop_recorder= (Button) findViewById(R.id.btn_stop_recorder);
        btn_play= (Button) findViewById(R.id.btn_play);
        btn_recorder.setOnClickListener(this);
        btn_stop_recorder.setOnClickListener(this);
        btn_play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_recorder:
                if (audioRecorder==null){
                    audioRecorder = new AudioRecorder();
                }
                try {
                    audioRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop_recorder:
                if (audioRecorder!=null)
                audioRecorder.stop();
                break;
            case R.id.btn_play:
                if (mPlayer ==null){
                    mPlayer =new AudioPlayer();
                    mPlayer.setPlayerPath(audioRecorder.getPath());
                }
                mPlayer.play();
                break;
        }
    }
}
