package com.jikexueyuan.audiorecorder_1131;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by fangc on 2016/4/16.
 */
public class AudioPlayer {
    private MediaPlayer mediaPlayer;
    private String playerPath;

    public AudioPlayer() {
    }

    public void setPlayerPath(String playerPath) {
        this.playerPath = playerPath;
    }

    public void play() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(playerPath);
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {//添加
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer = null;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
