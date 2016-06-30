package com.jikexueyuan.usingasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text= (TextView) findViewById(R.id.textView);
        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readURL("http://www.baidu.com");
            }
        });
    }
    public void readURL(String url){
        new AsyncTask<String, Float, String>() { //参数1.是doInBackground方法的形式参数url的类型；
        // 参数2.是过程参数，是onProgressUpdate回调方法的形式参数的类型。参数3.为结果参数,也是onPostExecute与
        // onCancelled回调方法的形式参数的类型。
            @Override
            protected String doInBackground(String... params) { //与线程中的run方法的功能类似。params[0]就是
            // AsyncTask()的第一个参数的类型，!!也就是readURL()的参数url。
            // 不能在此操作UI。与UI的互动/操作 在下边的回调函数中。
                try {
                    URL url1=new URL(params[0]);
                    URLConnection connection=url1.openConnection();
                    long total=connection.getContentLength();  //获取链接内容总长度。
                    InputStream iStream=connection.getInputStream(); //之前没见过的 InputStream获取源！！！
                    InputStreamReader isr=new InputStreamReader(iStream);
                    BufferedReader br=new BufferedReader(isr);
                    String line;
                    StringBuilder builder=new StringBuilder();
                    while ((line=br.readLine())!=null){
                        builder.append(line);
                        publishProgress((float)(builder.length() /total));//计算并发布task的进度
                    }
                    br.close();
                    isr.close();
                    iStream.close();
                    return builder.toString(); //将builder转换为字符串返回，之后AsyncTask匿名对象会自动调用回调方法
                    // （在第60-84行），完成后续操作
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() { //于onPostExecute方法前执行
                Toast.makeText(MainActivity.this,"开始读取",Toast.LENGTH_LONG).show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) { //最后一个执行的回调方法，参数为doInBackground()的返回值。
                text.setText(s);
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(Float... values) {//执行任务过程中，对外发布执行的进度，参数类型
                // 与AsyncTask的第二个需要一致。
                System.out.println(Arrays.toString(values));
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute(url);
    }
}
