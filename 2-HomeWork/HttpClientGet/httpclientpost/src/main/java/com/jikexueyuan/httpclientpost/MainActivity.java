package com.jikexueyuan.httpclientpost;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//4 使用HttpClient进行Post方式通讯实验；用jave EE做了个localHost程序，详见视频。(本课时
// 讲解使用HttpClient进行Post方式通信，通过HttpClient建立网络链接，使用HttpPost方法传出...)
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
                readNet("http://10.0.2.2:8080/MyWebTest/Do",et.getText().toString());
//                readNet("http://10.0.2.2:8080/MyWebTest/Do","name="+et.getText());//10.0.2.2 是通过模拟器访问
                // 本地服务器的地址(在本机上可直接通过LocalHost代替)，然而并没有预先创建好的MyWebTest中的Do，
                // 所以暂时不能得到实验结果。（问号后是传给服务器的信息。）;由于"name="这个键值已经在62行指定，这里需去掉它。
            }
        });
    }

    public void readNet( String url,String in) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {//params[0]就是url,params[1]是in。也是
            // AsyncTask()的第一个参数的类型。
                String urlString = params[0];
//                Log.i("params[0]=",params[0]);
//                Log.i("params[1]=",params[1]);
                HttpPost post=new HttpPost(urlString);
                /* StringEntity entity=new StringEntity(params[1]);
                 post.setEntity(entity); //post方法发送内容包HttpEntity对象；其非表单类型，
                 当前测试用服务器无法读取到。*/
                List<BasicNameValuePair> list=new ArrayList<BasicNameValuePair>();//（NameValuePair：参数为键值对类型）
                list.add(new BasicNameValuePair("name",params[1]));
                try {
                    post.setEntity(new UrlEncodedFormEntity(list));//匿名对象为：将list中的所有键值对
                    // 编码成为一个标准的表单类型。然后由post对象将之发送HttpRequest。
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    HttpResponse response = client.execute(post);
                    String value = EntityUtils.toString(response.getEntity());//从当前的http的互联网请求中读取
                    // 到的数据，getEntity()得到的 传输内容包对象，它不能直接转化为字符串，所以用EntityUtils。
//                    System.out.println(value);
                    return value;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                text.setText(s);
//                super.onPostExecute(s);
            }
        }.execute(url,in);
    }
}