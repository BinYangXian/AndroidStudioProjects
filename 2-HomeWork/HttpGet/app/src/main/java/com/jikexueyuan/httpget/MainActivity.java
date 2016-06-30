package com.jikexueyuan.httpget;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/*版本：1.1，请求方式：get，编码方式：utf-8

主要功能：中英互译，同时获得有道翻译结果和有道词典结果（可能没有）

参数说明：

　type - 返回结果的类型，固定为data

　doctype - 返回结果的数据格式，xml或json或jsonp

　version - 版本，当前最新版本为1.1

　q - 要翻译的文本，必须是UTF-8编码，字符长度不能超过200个字符，需要进行urlencode编码

　only - 可选参数，dict表示只获取词典数据，translate表示只获取翻译数据，默认为都获取

　注： 词典结果只支持中英互译，翻译结果支持英日韩法俄西到中文的翻译以及中文到英语的翻译

errorCode：

　0 - 正常

　20 - 要翻译的文本过长

　30 - 无法进行有效的翻译

　40 - 不支持的语言类型

　50 - 无效的key

　60 - 无词典结果，仅在获取词典结果生效
*/
//一般网页访问数据的方式是用的该get方式，
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
                            Log.i("params[0]=", params[0]);
                            URLConnection connection = url.openConnection();
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
                    // 返回数据格式为json
//                }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=textHttplasdGet&key=1310740987&type=data&doctype=json&version=1.1&q=good ");
                    //返回数据格式为xml,q="要翻译的文本"，当然需要时英文才能正常翻译
                }.execute("http://fanyi.youdao.com/openapi.do?keyfrom=textHttplasdGet&key=1310740987&type=data&doctype=xml&version=1.1&q=fuck");

            }
        });
    }
}
