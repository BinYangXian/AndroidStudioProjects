package com.jikexueyuan.httppost;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//一般网页访问数据的方式是用的get方式，此处采用的是POST方式与网络交互通信。
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>() {

                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            URL url = new URL(params[0]);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                            //connection的配置
                            connection.setDoInput(true);//默认可以不写
                            connection.setDoOutput(true);
                            connection.setRequestMethod("POST");//指定请求方法为POST方法。
                            //数据输出，这种数据请求方式意味着信息量可能很大（相对于get方法）
                            OutputStreamWriter osw=new OutputStreamWriter(connection.getOutputStream(),"utf-8");
                            BufferedWriter bw=new BufferedWriter(osw);
                            //请求数据，注意去句首"?"号
                            bw.write("keyfrom=textHttplasdGet&key=1310740987&type=data&doctype=xml&version=1.1&q=good");

                            bw.flush(); //手动冲刷出缓存中数据，以防输出不完整。
                            //读取数据
                            InputStream is = connection.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is, "utf-8");
                            BufferedReader br = new BufferedReader(isr);
                            String line;
                            while ((line = br.readLine()) != null) {
                                System.out.println(line + "\n");
                            }
                            br.close();
                            isr.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    // 返回数据格式为json,在http的get方式读取网络数据时，"?"后面的是向服务器传递的数据；而post方式是
                    // 通过connection、上述35-44行传递给服务器。
//                }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=textHttplasdGet&key=1310740987&type=data&doctype=json&version=1.1&q=good ");
                    //返回数据格式为xml
//                }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=textHttplasdGet&key=1310740987&type=data&doctype=xml&version=1.1&q=good ");
                }.execute("http://fanyi.youdao.com/openapi.do");

            }
        });
    }
}
