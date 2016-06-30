package com.jikexueyuan.audiorecorder_1131;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by fangc on 2016/4/16.
 */
public class AudioRecorder {

    private String dir;
    private String fileName;
    private MediaRecorder mediaRecorder;

    public AudioRecorder(){

        dir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/myRecorder/";

    }
    public void start() throws IOException {
        String state=Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            throw new IOException("没有可用的存储空间");
        }
        File myDir=new File(dir);
        if (!myDir.exists()){
            myDir.mkdir();
            Log.i("Note", "文件夹创建成功");
            Log.i("Note",Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        //音频录制设置规范
        fileName=dir+System.currentTimeMillis()+".amr";
        mediaRecorder=new MediaRecorder();//实例化
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setAudioSamplingRate(8000);//采样速率
        mediaRecorder.setOutputFile(fileName);

        mediaRecorder.prepare();
        mediaRecorder.start();
    }
    public void stop(){
        if (mediaRecorder!=null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }
    }
    public String getPath(){
        return fileName;
    }
}
