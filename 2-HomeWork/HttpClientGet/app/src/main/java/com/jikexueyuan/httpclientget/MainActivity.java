package com.jikexueyuan.httpclientget;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
//4 本课时讲解使用HttpClient进行Get方式通信，通过HttpClient建立网络链接，使用HttpGet方法读取数...
public class MainActivity extends AppCompatActivity {

    private EditText et;
    private TextView text;

    HttpClient client; //这个是老类，需要添加支持类库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = new DefaultHttpClient();

        et = (EditText) findViewById(R.id.et);
        text = (TextView) findViewById(R.id.textView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNet("http://10.0.2.2:8080/MyWebTest/Do?name="+et.getText());//10.0.2.2 是通过模拟器访问
                // 本地服务器的地址，然而并没有预先创建好的MyWebTest中的Do，所以暂时不能得到实验结果。
                // （问号后是传给服务器的信息。）
            }
        });
    }

    public void readNet(String url) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String urlString = params[0];
                HttpGet get = new HttpGet(urlString);//HttpGet的对象是继承了HttpRequestBase的

                try {
                    HttpResponse response = client.execute(get);//请求后，当然是得到HttpResponse对象
                    String value = EntityUtils.toString(response.getEntity());//从当前的http的互联网请求的答复中读取
                    // 到的数据，但是getEntity()得到 传输内容的工具对象，它不能直接转化为字符串，所以用EntityUtils。
                    Log.i("Response：",value);
                    return value;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) { //s为上述方法返回的value。
                text.setText(s);
//                super.onPostExecute(s);
            }
        }.execute(url);
    }
}
